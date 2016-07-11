package org.octopus.dashboard.freemarker;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.octopus.dashboard.shared.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerTemplate implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(FreemarkerTemplate.class);
	private String templatePath;
	private String templateEncoding;
	private Configuration freemarkerConfiguration;
	private Template template;

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateEncoding() {
		return templateEncoding;
	}

	public void setTemplateEncoding(String templateEncoding) {
		this.templateEncoding = templateEncoding;
	}

	public Configuration getFreemarkerConfiguration() {
		return freemarkerConfiguration;
	}

	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;

	}

	/**
	 * 
	 * @param model
	 *            be a Map or Model..
	 * @return
	 */
	public String generateContent(Object model) {
		return generateContent(template, model);
	}

	private String generateContent(Template template, Object model) {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		} catch (TemplateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return StringUtils.EMPTY;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (template == null) {
			logger.debug("trying to create template, looking for templatePath setting and freemarkerConfiguration.");
			if (freemarkerConfiguration == null || templatePath == null) {
				logger.error("missing templatePath or freemarkerConfiguration, can't build template");
			} else {
				freemarkerConfiguration.setSettings(buildSettings());
				if (templateEncoding == null)
					templateEncoding = Constants.UTF8.name();
				logger.debug("trying to apply template encoding:" + templateEncoding);
				template = freemarkerConfiguration.getTemplate(templatePath, templateEncoding);
			}
		} else {
			logger.info("template already set, ignore the templatePath and freemarkerConfiguration.");
		}
	}

	private Properties buildSettings() {
		Properties settings = new Properties();
		settings.put("classic_compatible", "true");
		settings.put("whitespace_stripping", "true");
		settings.put("template_update_delay", "300");
		settings.put("locale", "zh_CN");
		settings.put("default_encoding", "UTF-8");
		settings.put("url_escaping_charset", "UTF-8");
		settings.put("date_format", "yyyy-MM-dd");
		settings.put("time_format", "HH:mm:ss");
		settings.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		settings.put("number_format", "#");
		settings.put("boolean_format", "true,false");
		settings.put("output_encoding", "UTF-8");
		settings.put("tag_syntax", "auto_detect");
		return settings;
	}
}
