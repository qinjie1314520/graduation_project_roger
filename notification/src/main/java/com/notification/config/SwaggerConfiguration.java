package com.notification.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *  swagger2配置 默认地址http://localhost:10004/swagger-ui.html
 * @author
 * @date 21:05 2020/3/17
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "mconfig", name = "swagger-ui-open", havingValue = "true")
public class SwaggerConfiguration {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.notification.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("graduation.project.roger.notification Restful APIs")
				.description("restful apis docs")
				.termsOfServiceUrl("180.102.132.35:10004").version("1.0").build();
	}
}
