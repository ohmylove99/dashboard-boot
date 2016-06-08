package org.octopus.dashboard.service.email;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class MimeMailService {
	protected static final String DEFAULT_ENCODING = "utf-8";
	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	protected Template template;

	@Autowired
	private JavaMailSender mailSender;

	protected Configuration freemarkerConfiguration;

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		this.freemarkerConfiguration = freemarkerConfiguration;
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
	}

	public String generateContent(Map map) throws MessagingException {
		return generateContent(template, map);

	}

	public String generateContent(Template template, Map map) throws MessagingException {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new MessagingException(e.getMessage());
		} catch (TemplateException e) {
			logger.error(e.getMessage());
			throw new MessagingException(e.getMessage());
		}
	}

	public File generateAttachment() throws MessagingException {
		return generateAttachment(new ClassPathResource("/email/mailAttachment.txt"));
	}

	public File generateAttachment(Resource resource) throws MessagingException {
		try {
			return resource.getFile();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new MessagingException("no attachment", e);
		}
	}

	public void sendMimeMail(String from, String[] to, String[] cc, String subject, String content, boolean isHtml) {
		MimeMessage mailMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, DEFAULT_ENCODING);

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setCc(cc);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, isHtml);

			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}

	}

	public void sendMimeMail(String from, String[] to, String[] cc, String subject, String content, String templatePath,
			Map templateMap, boolean isHtml) {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		try {
			Template tmplt = freemarkerConfiguration.getTemplate(templatePath, DEFAULT_ENCODING);
			String cnt = generateContent(tmplt, templateMap);
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, DEFAULT_ENCODING);

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setCc(cc);
			messageHelper.setSubject(subject);
			messageHelper.setText(cnt, isHtml);

			mailSender.send(mailMessage);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}

	}

	public void sendMimeMail(String from, String[] to, String[] cc, String subject, String content, String templatePath,
			Map templateMap, boolean isHtml, File[] inlineFiles, File[] attachFiles) {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		try {
			Template tmplt = freemarkerConfiguration.getTemplate(templatePath, DEFAULT_ENCODING);
			String cnt = generateContent(tmplt, templateMap);
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, DEFAULT_ENCODING);

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setCc(cc);
			messageHelper.setSubject(subject);
			messageHelper.setText(cnt, isHtml);

			addInlines(inlineFiles, messageHelper);

			addAttachments(attachFiles, messageHelper);

			mailSender.send(mailMessage);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}

	}

	public void sendMimeMail(String from, String[] to, String[] cc, String subject, String content, boolean isHtml,
			File[] inlineFiles, File[] attachFiles) {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		try {
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setCc(cc);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, isHtml);

			addInlines(inlineFiles, messageHelper);

			addAttachments(attachFiles, messageHelper);

			mailSender.send(mailMessage);
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		}

	}

	private void addAttachments(File[] attachFiles, MimeMessageHelper messageHelper) throws MessagingException {
		if (attachFiles != null) {
			for (File file : attachFiles) {
				FileSystemResource fsr = new FileSystemResource(file);
				messageHelper.addAttachment(getAttachmentFileName(file), fsr);
			}
		}
	}

	private void addInlines(File[] inlineFiles, MimeMessageHelper messageHelper) throws MessagingException {
		if (inlineFiles != null) {
			for (File file : inlineFiles) {
				FileSystemResource fsr = new FileSystemResource(file);
				messageHelper.addInline(getInlineContentId(file), fsr);
			}
		}
	}

	private String getInlineContentId(File file) {
		return file.getName();
	}

	private String getAttachmentFileName(File file) {
		return file.getName();
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
