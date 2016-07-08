package org.octopus.dashboard.rest.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.octopus.dashboard.domain.entity.Upload;
import org.octopus.dashboard.rest.AccountRestController;
import org.octopus.dashboard.service.UploadService;
import org.octopus.dashboard.shared.utils.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@PropertySource("classpath:application-${spring.profiles.default}.properties")
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource(value = "classpath:application-${spring.profiles.default}.properties", ignoreResourceNotFound = true), })
public class FileUploadController implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);
	private LinkedList<FileMeta> files = new LinkedList<FileMeta>();
	private FileMeta fileMeta;
	@Autowired
	@Qualifier("foldersConfig")
	private PropertiesFactoryBean foldersConfig;
	private String uploadPath;
	private String downloadPath;
	@Autowired
	private UploadService uploadServcie;

	@RequestMapping(value = "/api/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request,
			HttpServletResponse response) {

		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			logger.info(mpf.getOriginalFilename() + " uploaded! " + files.size());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
			fileMeta.setFileType(mpf.getContentType());

			try {
				fileMeta.setBytes(mpf.getBytes());
				String fileLocation = uploadPath + "/" + Ids.uuid2() + "/" + mpf.getOriginalFilename();
				logger.debug("trying to save file to:" + fileLocation);
				File file = new File(fileLocation);
				if (!file.exists()) {
					FileUtils.forceMkdir(file);
				}
				FileCopyUtils.copy(mpf.getBytes(),
						new FileOutputStream(fileLocation + "/" + mpf.getOriginalFilename()));
				logger.debug("file save to:" + fileLocation);

				Upload upload = fileMeta.buildEntity();
				upload.setFileLocation(fileLocation);

				Upload result = uploadServcie.save(upload);

				result.setFileLink("/api/upload/" + result.getId());
				result.setFileLocation(fileLocation);

				fileMeta.setId(result.getId());

				uploadServcie.save(result);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			// 2.4 add to files
			files.add(fileMeta);

		}

		return files;

	}

	@RequestMapping(value = "/api/upload/{id}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable Long id) {

		try {
			Upload upload = uploadServcie.get(id);
			response.setContentType(upload.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\"" + upload.getFileName() + "\"");
			FileCopyUtils.copy(upload.getContent(), response.getOutputStream());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		uploadPath = foldersConfig.getObject().getProperty("uploadFolder");
		downloadPath = foldersConfig.getObject().getProperty("uploadFolder");
		logger.debug("set uploadPath:" + uploadPath);
		logger.debug("set downloadPath:" + downloadPath);
	}
}
