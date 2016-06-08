package org.octopus.dashboard.service.recruit;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;

import org.octopus.dashboard.service.email.MailService;
import org.octopus.dashboard.service.email.MimeMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;

@Component
public class RecruitMailService extends MimeMailService {
	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	@Override
	public File generateAttachment() throws MessagingException {
		try {
			Resource resource = new ClassPathResource("/email/mailAttachment.txt");
			return resource.getFile();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new MessagingException("no attachment", e);
		}
	}

	@Override
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		this.freemarkerConfiguration = freemarkerConfiguration;
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
	}
}
