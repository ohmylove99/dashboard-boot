package org.octopus.dashboard.domain.email.model;

import java.io.File;

public class EmailMimeModel extends EmailModel {
	private boolean isHtml;
	private String[] inlineFilesLocation;
	private String[] attachFilesLocation;
	private File[] inlineFiles;
	private File[] attachFiles;

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public File[] getInlineFiles() {
		return inlineFiles;
	}

	public void setInlineFiles(File[] inlineFiles) {
		this.inlineFiles = inlineFiles;
	}

	public File[] getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(File[] attachFiles) {
		this.attachFiles = attachFiles;
	}

	public String[] getInlineFilesLocation() {
		return inlineFilesLocation;
	}

	public void setInlineFilesLocation(String[] inlineFilesLocation) {
		this.inlineFilesLocation = inlineFilesLocation;
	}

	public String[] getAttachFilesLocation() {
		return attachFilesLocation;
	}

	public void setAttachFilesLocation(String[] attachFilesLocation) {
		this.attachFilesLocation = attachFilesLocation;
	}

}
