package org.octopus.dashboard.service.email;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.octopus.dashboard.service.recruit.RecruitMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "/email/applicationContext-email.xml" })
public class MimeMailServiceTest extends AbstractJUnit4SpringContextTests {
	protected static final String DEFAULT_ENCODING = "utf-8";
	private static Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	protected RecruitMailService mimeMailService;

	@Test
	public void sendMail() throws MessagingException, InterruptedException, IOException {
		try {
			mimeMailService.sendMimeMail("dashboard@octopus.org", new String[] { "jason@octopus.org" }, new String[] {},
					"mimesubject", "content", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void sendTemplateMail() throws MessagingException, InterruptedException, IOException {
		try {
			Map map = new HashMap();
			map.put("name", "Jason");
			mimeMailService.sendMimeMail("dashboard@octopus.org", new String[] { "jason@octopus.org" }, new String[] {},
					"mimesubject", "content", "mailTemplate.ftl", map, true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void sendTemplateAttachmentMail() throws MessagingException, InterruptedException, IOException {
		try {
			Map map = new HashMap();
			map.put("name", "Jason");
			File f1 = new File("c:\\dev\\test.txt");
			File[] attachments = new File[] { f1 };
			mimeMailService.sendMimeMail("dashboard@octopus.org", new String[] { "jason@octopus.org" }, new String[] {},
					"mimesubject", "content", "mailTemplate.ftl", map, true, null, attachments);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
