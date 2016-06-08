package org.octopus.dashboard.web.upload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public String displayForm() {
		return "upload/file_upload_form";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String save(@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();

		if (null != files && files.size() > 0) {
			for (MultipartFile multipartFile : files) {

				String fileName = multipartFile.getOriginalFilename();
				fileNames.add(fileName);
				// Handle file content - multipartFile.getInputStream()
				// TODO do parse lucene

			}
		}

		map.addAttribute("files", fileNames);
		return "upload/file_upload_success";
	}
}
