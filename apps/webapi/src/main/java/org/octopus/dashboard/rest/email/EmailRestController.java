package org.octopus.dashboard.rest.email;

import org.octopus.dashboard.domain.email.model.EmailMimeModel;
import org.octopus.dashboard.domain.email.model.EmailModel;
import org.octopus.dashboard.domain.email.model.EmailTemplateModel;
import org.octopus.dashboard.service.email.EmailMimeService;
import org.octopus.dashboard.service.email.EmailService;
import org.octopus.dashboard.shared.constants.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unchecked")
@RestController
public class EmailRestController {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(EmailRestController.class);

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailMimeService emailMimeService;

	@RequestMapping(value = "/api/email", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<String> create(@RequestBody EmailModel model) {
		try {
			emailService.sendMail(model);
			return new ResponseEntity<String>("ok", new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("fail", new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/api/email/1", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public EmailModel getEmailModel1() {
		EmailModel model = new EmailModel();
		model.setFrom("from@localhost.com");
		model.setTo("ohmylove99@hotmail.com");
		model.setCcs(new String[] { "ohmylove99@hotmail.com", "lengfei99@hotmail.com" });
		model.setText("my text");
		return model;
	}

	@RequestMapping(value = "/api/email/2", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public EmailModel getEmailModel2() {
		EmailModel model = new EmailModel();
		model.setFrom("from@localhost.com");
		model.setTo("ohmylove99@hotmail.com");
		model.setCcs(new String[] { "ohmylove99@hotmail.com", "lengfei99@hotmail.com" });
		model.setText("my text");
		EmailTemplateModel emailTemplateMode = new EmailTemplateModel();
		emailTemplateMode.setTemplatePath("mailTemplate.ftl");
		emailTemplateMode.getMap().put("username", "Jason");
		model.setEmailTemplateModel(emailTemplateMode);
		return model;
	}

	@RequestMapping(value = "/api/mimemail/1", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public EmailMimeModel getEmailMimeModel1() {
		EmailMimeModel model = new EmailMimeModel();
		model.setFrom("from@localhost.com");
		model.setTo("ohmylove99@hotmail.com");
		model.setCcs(new String[] { "ohmylove99@hotmail.com", "lengfei99@hotmail.com" });
		model.setText("<br><p1>Hello</p>");
		model.setHtml(true);
		EmailTemplateModel emailTemplateMode = new EmailTemplateModel();
		emailTemplateMode.setTemplatePath("mailTemplate.ftl");
		emailTemplateMode.getMap().put("username", "Peter");
		model.setEmailTemplateModel(emailTemplateMode);
		model.setAttachFilesLocation(new String[] { "classpath:/email/mailAttachment.txt" });
		return model;
	}

	@RequestMapping(value = "/api/mimemail", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<String> create(@RequestBody EmailMimeModel model) {
		try {
			emailMimeService.sendMail(model);
			return new ResponseEntity<String>("ok", new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("fail", new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
		}
	}
}
