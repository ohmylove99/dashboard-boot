package org.octopus.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.dashboard.BootApiApplication;
import org.octopus.dashboard.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
@Rollback(true)
@SqlConfig(transactionManager = "transactionManager")
public class AccountServiceTest {
	@Autowired
	private AccountService accountService;

	@Test
	public void login() {
		String token = accountService.login("admin", "admin");
		assertThat(token).isNotEmpty();
	}

	@Test
	@Transactional
	public void getRoles() {
		List<Role> roles = accountService.getRoles(1L);
		assertThat(roles.get(0).getName()).isEqualToIgnoringCase("admin");
	}
	@Test
	public void isExsit() {
		assertThat(accountService.isExsit("admin")).isTrue();
	}
	@Test
	@Transactional
	public void register() {
		String loginName = "perter";
		String name = "Perter Chen";
		String password = "xxx";
		accountService.register(loginName, name, password);
		assertThat(accountService.isExsit("perter")).isTrue();
	}
}
