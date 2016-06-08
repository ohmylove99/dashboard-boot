package org.octopus.dashboard.service.spring;

import java.util.List;

import org.octopus.dashboard.model.TProject;

public interface ProjectService {
	public boolean save(TProject obj);

	public boolean delete(long id);

	public TProject get(long id);

	public List<TProject> gets();
}
