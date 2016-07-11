package org.octopus.dashboard.rest.email;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

import org.octopus.dashboard.domain.email.model.EmailModel;
import org.octopus.dashboard.freemarker.FreemarkerTemplate;
import org.octopus.dashboard.shared.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Autowired
	protected JavaMailSender mailSender;

	@Autowired
	protected FreemarkerTemplate freemarker;

	public void sendMail(EmailModel model) {
		SimpleMailMessage mail = prepareMessage(model);
		mail.setText(model.getText());

		mailSender.send(mail);
	}

	public void sendMail(EmailModel model, Object templateModel) {
		SimpleMailMessage mail = prepareMessage(model);
		mail.setText(freemarker.generateContent(templateModel));

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
			mail.setTo(model.getCc());
		} else {
			mail.setTo(model.getCcs());
		}
		if (model.getBccs() == null) {
			mail.setTo(model.getBcc());
		} else {
			mail.setTo(model.getBccs());
		}
	}

	public FreemarkerTemplate getFreemarker() {
		return freemarker;
	}

	public void setFreemarker(FreemarkerTemplate freemarker) {
		this.freemarker = freemarker;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}