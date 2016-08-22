package org.octopus.dashboard.service.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.octopus.dashboard.domain.email.model.EmailModel;
import org.octopus.dashboard.domain.email.model.EmailTemplateModel;
import org.octopus.dashboard.freemarker.FreemarkerTemplate;
import org.octopus.dashboard.shared.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);

	protected JavaMailSender mailSender;

	protected Configuration freemarkerConfiguration;

	public FreemarkerTemplate buildFreemarker(String templatePath) {
		FreemarkerTemplate freemarkerTemplate = new FreemarkerTemplate();
		try {
			Template template = freemarkerConfiguration.getTemplate(templatePath, Constants.UTF8.name());
			freemarkerTemplate.setTemplate(template);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return freemarkerTemplate;
	}

	public void sendMail(EmailModel model) {
		String text = StringUtils.EMPTY;
		if (model.getEmailTemplateModel() != null && model.getEmailTemplateModel().getTemplatePath() != null) {
			EmailTemplateModel emailTemplateModel = model.getEmailTemplateModel();
			FreemarkerTemplate freemarker = buildFreemarker(emailTemplateModel.getTemplatePath());
			text = freemarker.generateContent(emailTemplateModel.getMap());
		} else {
			text = model.getText();
		}
		SimpleMailMessage mail = prepareMessage(model);
		mail.setText(text);

		mailSender.send(mail);
	}

	private SimpleMailMessage prepareMessage(EmailModel model) {
		SimpleMailMessage mail = new SimpleMailMessage();
		setSender(model, mail);
		setSubject(model);
		return mail;
	}

	protected void setSubject(EmailModel model) {
		try {
			model.setSubject(MimeUtility.encodeText(model.getSubject(), Constants.UTF8.name(), "B"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
	}

	private void setSender(EmailModel model, SimpleMailMessage mail) {
		mail.setFrom(model.getFrom());
		if (model.getTos() == null) {
			mail.setTo(model.getTo());
		} else {
			mail.setTo(model.getTos());
		}
		if (model.getCcs() == null) {
			mail.setCc(model.getCc());
		} else {
			mail.setCc(model.getCcs());
		}
		if (model.getBccs() == null) {
			mail.setBcc(model.getBcc());
		} else {
			mail.setBcc(model.getBccs());
		}
	}
}