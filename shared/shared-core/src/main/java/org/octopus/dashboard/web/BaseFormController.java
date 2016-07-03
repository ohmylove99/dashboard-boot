package org.octopus.dashboard.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.octopus.dashboard.shared.constants.Constants;
import org.octopus.dashboard.shared.web.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseFormController implements ServletContextAware {
	private MessageSourceAccessor messages;
	private ServletContext servletContext;

	protected final transient static Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	protected String cancelView;
	protected String successView;

	public static final String MESSAGES_KEY = "successMessages";
	public static final String ERRORS_MESSAGES_KEY = "errors";

	@Autowired(required = false)
	protected Validator validator;

	@Autowired
	public void setMessages(MessageSource messageSource) {
		messages = new MessageSourceAccessor(messageSource);
	}

	public void saveError(HttpServletRequest request, String error) {
		List errors = (List) request.getSession().getAttribute(ERRORS_MESSAGES_KEY);
		if (errors == null) {
			errors = new ArrayList();
		}
		errors.add(error);
		request.getSession().setAttribute(ERRORS_MESSAGES_KEY, errors);
	}

	public void saveMessage(HttpServletRequest request, String msg) {
		List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

		if (messages == null) {
			messages = new ArrayList();
		}

		messages.add(msg);
		request.getSession().setAttribute(MESSAGES_KEY, messages);
	}

	/**
	 * Convenience method for getting a i18n key's value. Calling
	 * getMessageSourceAccessor() is used because the RequestContext variable is
	 * not set in unit tests b/c there's no DispatchServlet Request.
	 *
	 * @param msgKey
	 * @param locale
	 *            the current locale
	 * @return
	 */
	public String getText(String msgKey, Locale locale) {
		return messages.getMessage(msgKey, locale);
	}

	/**
	 * Convenient method for getting a i18n key's value with a single string
	 * argument.
	 *
	 * @param msgKey
	 * @param arg
	 * @param locale
	 *            the current locale
	 * @return
	 */
	public String getText(String msgKey, String arg, Locale locale) {
		return getText(msgKey, new Object[] { arg }, locale);
	}

	/**
	 * Convenience method for getting a i18n key's value with arguments.
	 *
	 * @param msgKey
	 * @param args
	 * @param locale
	 *            the current locale
	 * @return
	 */
	public String getText(String msgKey, Object[] args, Locale locale) {
		return messages.getMessage(msgKey, args, locale);
	}

	/**
	 * Convenience method to get the Configuration HashMap from the servlet
	 * context.
	 *
	 * @return the user's populated form from the session
	 */
	public Map getConfiguration() {
		Map config = (HashMap) servletContext.getAttribute(Constants.CONFIG);

		// so unit tests don't puke when nothing's been set
		if (config == null) {
			return new HashMap();
		}

		return config;
	}

	/**
	 * Set up a custom property editor for converting form inputs to real
	 * objects
	 * 
	 * @param request
	 *            the current request
	 * @param binder
	 *            the data binder
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format", request.getLocale()));
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	public final BaseFormController setCancelView(String cancelView) {
		this.cancelView = cancelView;
		return this;
	}

	public final String getCancelView() {
		// Default to successView if cancelView is invalid
		if (this.cancelView == null || this.cancelView.length() == 0) {
			return getSuccessView();
		}
		return this.cancelView;
	}

	public final String getSuccessView() {
		return this.successView;
	}

	public final BaseFormController setSuccessView(String successView) {
		this.successView = successView;
		return this;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	protected ServletContext getServletContext() {
		return servletContext;
	}
}
