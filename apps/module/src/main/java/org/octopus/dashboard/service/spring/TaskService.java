package org.octopus.dashboard.service.spring;

import java.util.List;

import org.octopus.dashboard.model.TTask;

public interface TaskService {

	public boolean save(TTask obj);

	public boolean delete(long id);

	public TTask get(long id);

	public List<TTask> gets();
}
