package org.octopus.dashboard.service.spring.impl;

import java.util.List;

import org.octopus.dashboard.dao.spring.TaskAuditDao;
import org.octopus.dashboard.dao.spring.TaskDao;
import org.octopus.dashboard.model.TTask;
import org.octopus.dashboard.service.spring.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	@Qualifier("taskDaoImpl")
	private TaskDao taskDaoImpl;

	@Autowired
	@Qualifier("taskAuditDaoImpl")
	private TaskAuditDao taskAuditDaoImpl;

	@Override
	public boolean save(TTask task) {
		boolean rtn = taskDaoImpl.save(task);
		boolean rtnAudit = taskAuditDaoImpl.add(task.createAudit());
		return rtn && rtnAudit;
	}

	@Override
	public boolean delete(long id) {
		TTask task = taskDaoImpl.get(id);
		boolean rtn = taskDaoImpl.delete(id);
		boolean rtnAudit = taskAuditDaoImpl.add(task.createAudit());
		return rtn && rtnAudit;
	}

	@Override
	public TTask get(long id) {
		TTask task = taskDaoImpl.get(id);
		return task;
	}

	@Override
	public List<TTask> gets() {
		List<TTask> tasks = taskDaoImpl.gets();
		return tasks;
	}

}
