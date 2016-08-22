package org.octopus.dashboard.domain.email.model;

public class EmailModel {
	private String from;
	private String to;
	private String[] tos;
	private String cc;
	private String[] ccs;
	private String bcc;
	private String[] bccs;
	private String subject;
	private String text;
	private EmailTemplateModel emailTemplateModel;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String[] getTos() {
		return tos;
	}

	public void setTos(String[] tos) {
		this.tos = tos;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String[] getCcs() {
		return ccs;
	}

	public void setCcs(String[] ccs) {
		this.ccs = ccs;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String[] getBccs() {
		return bccs;
	}

	public void setBccs(String[] bccs) {
		this.bccs = bccs;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EmailTemplateModel getEmailTemplateModel() {
		return emailTemplateModel;
	}

	public void setEmailTemplateModel(EmailTemplateModel emailTemplateModel) {
		this.emailTemplateModel = emailTemplateModel;
	}

}
