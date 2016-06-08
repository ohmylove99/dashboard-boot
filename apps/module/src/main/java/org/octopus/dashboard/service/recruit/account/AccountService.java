package org.octopus.dashboard.service.recruit.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.hibernate.service.spi.ServiceException;
import org.octopus.dashboard.dao.recruit.TaskDaoRepository;
import org.octopus.dashboard.dao.recruit.UserDaoRepository;
import org.octopus.dashboard.model.recruit.User;
import org.octopus.dashboard.service.recruit.account.ShiroDbRealm.ShiroUser;
import org.octopus.dashboard.shared.security.Digests;
import org.octopus.dashboard.shared.utils.Encodes;
import org.octopus.dashboard.shared.utils.clock.ClockFactory;
import org.octopus.dashboard.shared.utils.clock.IClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDaoRepository userDao;
	private TaskDaoRepository taskDao;
	private IClock clock = ClockFactory.getClock();

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	public void registerUser(User user) {
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
	}

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("deleting super user", getCurrentUserName());
			throw new ServiceException("can't delete super user");
		}
		userDao.delete(id);
		taskDao.deleteByUserId(id);

	}

	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDaoRepository userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setTaskDao(TaskDaoRepository taskDao) {
		this.taskDao = taskDao;
	}

	public void setClock(IClock clock) {
		this.clock = clock;
	}
}
