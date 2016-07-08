package org.octopus.dashboard.rest.upload;

import org.octopus.dashboard.domain.entity.Upload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "bytes" })
public class FileMeta {
	private Long id;
	private String fileName;
	private String fileSize;
	private String fileType;
	private String fileLocation;

	private byte[] bytes;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Upload buildEntity() {
		Upload upload = new Upload();
		upload.setContent(getBytes());
		upload.setFileName(getFileName());
		upload.setFileType(getFileType());
		upload.setFileSize(getFileSize());
		return upload;
	}
}
