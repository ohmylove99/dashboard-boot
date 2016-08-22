package org.octopus.dashboard.domain.email.model;

import java.util.HashMap;
import java.util.Map;

public class EmailTemplateModel {
	private String templatePath;
	private Map map = new HashMap();

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
