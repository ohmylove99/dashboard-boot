package org.octopus.dashboard.rest.email;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.octopus.dashboard.domain.email.model.EmailMimeModel;
import org.octopus.dashboard.shared.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailMimeService extends EmailService implements ResourceLoaderAware {
	private static Logger logger = LoggerFactory.getLogger(EmailMimeService.class);
	private ResourceLoader resourceLoader;

	public void sendMail(EmailMimeModel model) {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, Constants.UTF8.name());

			prepareMessage(model, helper);

			helper.setText(model.getText(), model.isHtml());
			mailSender.send(msg);
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
	}

	public void sendMail(EmailMimeModel model, Object templateModel) {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, Constants.UTF8.name());

			prepareMessage(model, helper);

			helper.setText(freemarker.generateContent(templateModel), model.isHtml());
			mailSender.send(msg);
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
	}

	private void prepareMessage(EmailMimeModel model, MimeMessageHelper helper) throws MessagingException {
		setSender(model, helper);
		setSubject(model);
		loadFiles(model);
		addInlines(model.getInlineFiles(), helper);
		addAttachments(model.getAttachFiles(), helper);
	}

	public void setSender(EmailMimeModel model, MimeMessageHelper helper) {
		try {
			helper.setFrom(model.getFrom());
			if (model.getTos() == null) {
				helper.setTo(model.getTo());
			} else {
				helper.setTo(model.getTos());
			}
			if (model.getCcs() == null) {
				helper.setTo(model.getCc());
			} else {
				helper.setTo(model.getCcs());
			}
			if (model.getBccs() == null) {
				helper.setTo(model.getBcc());
			} else {
				helper.setTo(model.getBccs());
			}
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}
	}

	private void addAttachments(File[] attachFiles, MimeMessageHelper messageHelper) throws MessagingException {
		if (attachFiles != null) {
			for (File file : attachFiles) {
				FileSystemResource fsr = new FileSystemResource(file);
				try {
					messageHelper.addAttachment(MimeUtility.encodeWord(getAttachmentFileName(file)), fsr);
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	private void addInlines(File[] inlineFiles, MimeMessageHelper messageHelper) throws MessagingException {
		if (inlineFiles != null) {
			for (File file : inlineFiles) {
				FileSystemResource fsr = new FileSystemResource(file);
				try {
					messageHelper.addInline(MimeUtility.encodeWord(getInlineContentId(file)), fsr);
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	private String getInlineContentId(File file) {
		return file.getName();
	}

	private String getAttachmentFileName(File file) {
		return file.getName();
	}

	private void loadFiles(EmailMimeModel model) {
		loadInlineFiles(model);
		loadAttachmentFiles(model);
	}

	private void loadInlineFiles(EmailMimeModel model) {
		List<File> list = loadFiles(model.getInlineFilesLocation());
		if (list != null && list.size() > 0)
			model.setInlineFiles((File[]) list.toArray());
	}

	private void loadAttachmentFiles(EmailMimeModel model) {
		List<File> list = loadFiles(model.getAttachFilesLocation());
		if (list != null && list.size() > 0)
			model.setAttachFiles((File[]) list.toArray());
	}

	/**
	 * trying to load files from String[] args, this will reset File[]
	 * 
	 * @param resourcesPaths
	 */
	private List<File> loadFiles(String[] locations) {
		List<File> files = new ArrayList<File>();
		if (locations != null) {
			for (String location : locations) {
				logger.debug("Loading file from path:{}", location);
				Resource resource = resourceLoader.getResource(location);
				try {
					files.add(resource.getFile());
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return files;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}