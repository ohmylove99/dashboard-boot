package org.octopus.dashboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.octopus.dashboard.domain.entity.Role;
import org.octopus.dashboard.domain.entity.User;
import org.octopus.dashboard.repository.RoleDao;
import org.octopus.dashboard.repository.UserDao;
import org.octopus.dashboard.shared.constants.Constants;
import org.octopus.dashboard.shared.exception.ErrorCode;
import org.octopus.dashboard.shared.exception.ServiceException;
import org.octopus.dashboard.shared.utils.Ids;
import org.octopus.dashboard.shared.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Value("${app.loginTimeoutSecs:600}")
	private int loginTimeoutSecs;

	@Autowired
	private CounterService counterService;

	private Cache<String, User> loginUsers;

	@PostConstruct
	public void init() {
		loginUsers = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(loginTimeoutSecs, TimeUnit.SECONDS)
				.build();
	}

	@Transactional(readOnly = true)
	public String login(String loginName, String password) {
		User user = userDao.findByLoginName(loginName);

		if (user == null) {
			throw new ServiceException("User not exist", ErrorCode.UNAUTHORIZED);
		}

		if (!user.getPassword().equals(PasswordUtils.hashPassword(password))) {
			throw new ServiceException("Password wrong", ErrorCode.UNAUTHORIZED);
		}

		String token = Ids.uuid2();
		loginUsers.put(token, user);
		counterService.increment("loginUser");
		return token;
	}

	@Transactional(readOnly = true)
	public List<Role> getRoles(Long userId) {
		User user = userDao.findOne(userId);

		if (user == null) {
			throw new ServiceException("User not exist", ErrorCode.UNAUTHORIZED);
		}

		counterService.increment("getRolesByUser");
		List<Role> roles = user.getRoles();
		return roles;
	}

	public void logout(String token) {
		User user = loginUsers.getIfPresent(token);
		if (user == null) {
			logger.warn("logout an alreay logout token:" + token);
		} else {
			loginUsers.invalidate(token);
			counterService.decrement("loginUser");
		}
	}

	public User getLoginUser(String token) {

		User user = loginUsers.getIfPresent(token);

		if (user == null) {
			throw new ServiceException("User doesn't login", ErrorCode.UNAUTHORIZED);
		}

		return user;
	}

	public boolean isExsit(String loginName) {
		boolean rtn;
		User user = userDao.findByLoginName(loginName);
		rtn = user == null ? false : true;
		return rtn;
	}

	@Transactional
	public void register(String loginName, String name, String password) {

		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			throw new ServiceException("Invalid parameter", ErrorCode.BAD_REQUEST);
		}

		User user = userDao.findByLoginName(loginName);
		if (user != null) {
			throw new ServiceException("user already exsit", ErrorCode.BAD_REQUEST);
		}

		user = new User();
		user.setLoginName(loginName);
		user.setName(name);
		user.setPassword(PasswordUtils.hashPassword(password));
		user.setRegisterDate(new Date());

		Role role = roleDao.findOne((Long) Constants.Role_User_Key);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);

		userDao.save(user);
	}

}
