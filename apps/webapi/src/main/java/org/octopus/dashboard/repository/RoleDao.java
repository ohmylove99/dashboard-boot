package org.octopus.dashboard.repository;

import org.octopus.dashboard.domain.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Long> {
	Role findByName(String name);
}
