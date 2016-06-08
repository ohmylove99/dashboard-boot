package org.octopus.dashboard.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	protected static final String DEFAULT_ENCODING = "utf-8";
	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	protected JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String from, String[] to, String[] cc, String subject, String content) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setCc(cc);
		mail.setSubject(subject);
		mail.setText(content);

		mailSender.send(mail);
	}

}
