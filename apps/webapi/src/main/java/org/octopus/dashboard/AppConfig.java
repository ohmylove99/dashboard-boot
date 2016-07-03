package org.octopus.dashboard;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

@Configuration
@ComponentScan
public class AppConfig {

	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(openSessionInView());
		registration.addUrlPatterns("/*");

		return registration;
	}

	@Bean
	public Filter openSessionInView() {
		return new OpenSessionInViewFilter();
	}
}