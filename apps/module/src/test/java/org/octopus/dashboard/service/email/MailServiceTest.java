package org.octopus.dashboard.service.email;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "/email/applicationContext-email.xml" })
public class MailServiceTest extends AbstractJUnit4SpringContextTests {
	protected static final String DEFAULT_ENCODING = "utf-8";
	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	protected MailService mailService;

	@Test
	public void sendSimpleMail() throws MessagingException, InterruptedException, IOException {
		try {
			mailService.sendMail("dashboard@octopus.org", new String[] { "jason@octopus.org" }, new String[] {},
					"subject", "content");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
