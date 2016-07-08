package org.octopus.dashboard.repository;

import org.octopus.dashboard.domain.entity.Upload;
import org.springframework.data.repository.CrudRepository;

public interface UploadDao extends CrudRepository<Upload, Long> {
	Upload findByFileName(String fileName);
}
