package com.onehee.guestbook.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

//	Swagger-UI 2.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui.html
//	Swagger-UI 3.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui/index.html

	private String version = "V1";
	private String title = "GUEST BOOK API " + version;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
					.apiInfo(apiInfo()).groupName(version).select()
					.apis(RequestHandlerSelectors.basePackage("com.onehee.guestbook.api.controller"))
					.paths(regex("/.*")).build()
					.useDefaultResponseMessages(false);
	}
	
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<String>();
        consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<String>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
				// .description("<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/board/assets/img/ssafy_logo.png\" width=\"150\">") 
				.contact(new Contact("1-hee", "https://github.com/1-Hee", "onehee9710@gmail.com"))
				.license("MIT License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
				.version("1.0").build();
	}

}


