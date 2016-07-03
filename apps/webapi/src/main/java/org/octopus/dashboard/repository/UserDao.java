package org.octopus.dashboard.repository;

import org.octopus.dashboard.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
	User findByLoginName(String loginName);
}
