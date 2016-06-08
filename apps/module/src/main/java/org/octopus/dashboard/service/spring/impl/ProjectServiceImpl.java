package org.octopus.dashboard.service.spring.impl;

import java.util.List;

import org.octopus.dashboard.dao.spring.ProjectDao;
import org.octopus.dashboard.model.TProject;
import org.octopus.dashboard.service.spring.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	@Qualifier("projectDaoImpl")
	private ProjectDao projectDao;

	@Override
	public boolean save(TProject project) {
		return projectDao.save(project);
	}

	@Override
	public boolean delete(long id) {
		return projectDao.delete(id);
	}

	@Override
	public TProject get(long id) {
		return projectDao.get(id);
	}

	@Override
	public List<TProject> gets() {
		return projectDao.gets();
	}

}
