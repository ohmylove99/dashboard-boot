package org.octopus.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.dashboard.BootApiApplication;
import org.octopus.dashboard.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
public class AccountServiceTest {
	@Autowired
	private AccountService accountService;

	@Test
	public void login() {
		String token = accountService.login("admin", "admin");
		assertThat(token).isNotEmpty();
	}

	@Test
	public void getRoles() {
		List<Role> roles = accountService.getRoles(1L);
		assertThat(roles.get(0).getName()).isEqualToIgnoringCase("admin");
	}

	@Test
	public void register() {

	}
}
