package org.octopus.dashboard.rest;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.octopus.dashboard.service.AccountService;
import org.octopus.dashboard.shared.constants.MediaTypes;
import org.octopus.dashboard.shared.exception.ErrorCode;
import org.octopus.dashboard.shared.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);

	@Autowired
	private AccountService accountServcie;

	@RequestMapping(value = "/api/accounts/login", produces = MediaTypes.JSON_UTF_8)
	public Map<String, String> login(@RequestParam("loginName") String email,
			@RequestParam("password") String password) {

		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
			throw new ServiceException("User or password empty", ErrorCode.BAD_REQUEST);
		}

		String token = accountServcie.login(email, password);

		return Collections.singletonMap("token", token);
	}

	@RequestMapping(value = "/api/accounts/logout")
	public void logout(@RequestParam(value = "token", required = false) String token) {
		accountServcie.logout(token);
	}

	@RequestMapping(value = "/api/accounts/register")
	public void register(@RequestParam("loginName") String email,
			@RequestParam(value = "name", required = false) String name, @RequestParam("password") String password) {

		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
			throw new ServiceException("User or name or password empty", ErrorCode.BAD_REQUEST);
		}

		accountServcie.register(email, name, password);
	}
}
