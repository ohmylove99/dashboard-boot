package org.octopus.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = { "org.octopus.dashboard" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class),
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.web.bind.annotation.ControllerAdvice.class) })
@SpringBootApplication
public class BootApiApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootApiApplication.class, args);
	}
}
