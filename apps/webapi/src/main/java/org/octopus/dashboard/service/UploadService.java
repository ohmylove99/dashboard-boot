package org.octopus.dashboard.service;

import org.octopus.dashboard.domain.entity.Upload;
import org.octopus.dashboard.domain.entity.UploadMapping;
import org.octopus.dashboard.repository.UploadDao;
import org.octopus.dashboard.repository.UploadMappingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Autowired
	private UploadDao uploadDao;

	@Autowired
	private UploadMappingDao uploadMappingDao;

	@Transactional(readOnly = true)
	public Upload get(Long id) {
		return uploadDao.findOne(id);
	}

	@Transactional
	public Upload save(Upload upload) {
		return uploadDao.save(upload);
	}

	@Transactional
	public UploadMapping save(Upload upload, UploadMapping uploadMapping) {
		Upload upd = uploadDao.save(upload);
		uploadMapping.setUpload(upd);
		UploadMapping rtn = uploadMappingDao.save(uploadMapping);
		return rtn;
	}

	@Transactional
	public UploadMapping save(UploadMapping uploadMapping) {
		return uploadMappingDao.save(uploadMapping);
	}

	@Transactional
	public void save(Upload[] uploads) {
		if (uploads != null)
			for (Upload upload : uploads) {
				uploadDao.save(upload);
			}
	}

}
