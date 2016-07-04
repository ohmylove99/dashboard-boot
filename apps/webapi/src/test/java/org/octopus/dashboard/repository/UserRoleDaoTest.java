package org.octopus.dashboard.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.dashboard.BootApiApplication;
import org.octopus.dashboard.domain.entity.Role;
import org.octopus.dashboard.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApiApplication.class)
@DirtiesContext
@Rollback(true)
@SqlConfig(transactionManager = "transactionManager")
public class UserRoleDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Test
	public void findById() {
		List<User> users = (List<User>) userDao.findAll();
		assertThat(users).hasSize(1);

		User user = userDao.findByLoginName("admin");
		assertThat(user.getName()).isEqualToIgnoringCase("Admin");
		user = userDao.findOne(1L);
		assertThat(user.getName()).isEqualToIgnoringCase("Admin");

		List<Role> roles = (List<Role>) roleDao.findAll();
		assertThat(roles).hasSize(3);
		Role role = roleDao.findByName("admin");
		assertThat(role.getId()).isEqualTo(1);
	}

	@Test
	@Transactional
	public void findChild() {
		User user = userDao.findByLoginName("admin");
		assertThat(user.getRoles()).hasSize(1);
		assertThat(user.getRoles().get(0).getName()).isEqualToIgnoringCase("admin");
	}
}
