package org.octopus.dashboard.web.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.octopus.dashboard.model.recruit.Resume;
import org.octopus.dashboard.service.recruit.ResumeService;
import org.octopus.dashboard.shared.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResumeFileUploadController {

	@Autowired
	private ResumeService resumeService;

	@RequestMapping(value = "/uploadResumeForm", method = RequestMethod.GET)
	public String displayForm(@RequestParam(value = "id", required = false) String id, HttpServletRequest request) {
		request.setAttribute("id", id);
		return "upload/resume_upload_form";
	}

	@RequestMapping(value = "/uploadResume", method = RequestMethod.POST)
	public String save(@ModelAttribute("uploadResumeForm") FileUploadForm uploadForm, Model map,
			HttpServletRequest request) {

		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);
				upload(multipartFile, request, uploadForm);
				// Handle file content - multipartFile.getInputStream()
				// TODO do parse lucene

			}
		}

		map.addAttribute("files", fileNames);
		//return "upload/resume_upload_success";
		return "redirect:/resume/";
	}

	private void upload(MultipartFile file, HttpServletRequest request, FileUploadForm uploadForm) {
		// the directory to upload to
		String uploadDir = request.getServletContext().getRealPath("/upload");

		// The following seems to happen when running jetty:run
		if (uploadDir == null) {
			uploadDir = new File("src/main/webapp/resources").getAbsolutePath();
		}
		uploadDir += "/" + request.getRemoteUser() + "/";

		// Create the directory if it doesn't exist
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// retrieve the file data
		InputStream stream;
		try {
			stream = file.getInputStream();
			// write the file to the file specified
			OutputStream bos = new FileOutputStream(uploadDir + file.getOriginalFilename());
			int bytesRead;
			byte[] buffer = new byte[8192];

			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();

			// close the stream
			stream.close();

			// place the data into the request for retrieval on next page
			request.setAttribute("friendlyName", file.getName());
			request.setAttribute("fileName", file.getOriginalFilename());
			request.setAttribute("contentType", file.getContentType());
			request.setAttribute("size", file.getSize() + " bytes");
			request.setAttribute("location",
					dirPath.getAbsolutePath() + Constants.FILE_SEP + file.getOriginalFilename());

			String link = request.getContextPath() + "/upload" + "/" + request.getRemoteUser() + "/";
			request.setAttribute("link", link + file.getOriginalFilename());

			if (!org.apache.commons.lang3.StringUtils.isEmpty(uploadForm.getId())) {
				// update
				Resume resume = resumeService.getResume(Long.parseLong(uploadForm.getId()));
				resume.setUploadFileName(file.getOriginalFilename());
				resume.setUploadFileLink(link + file.getOriginalFilename());
				resumeService.saveResume(resume);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
