package org.octopus.dashboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/webapi/v2/api-docs
 *
 */
@EnableSwagger2
public class SwaggerConfig {
	/*@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/api/.*")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		String contact = "ohmylove9928";
		ApiInfo apiInfo = new ApiInfo("My Project's REST API", "This is a description of your API.", "Version 1.0",
				"API Terms", contact, "MIT", "");
		return apiInfo;
	}*/
}
