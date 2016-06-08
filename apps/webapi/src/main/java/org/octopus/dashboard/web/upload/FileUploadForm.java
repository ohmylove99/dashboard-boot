package org.octopus.dashboard.web.upload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {

	private String id;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	// Getter and setter methods
}