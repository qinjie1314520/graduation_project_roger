package com.authority.config;


import com.controller.config.ExceptionHandlerConfig;
import com.controller.validator.AopValidator;
import lombok.extern.slf4j.Slf4j;
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
 * 其他的需要注入aop容器的Bean
 * @author roger
 * @since  2020/11/3 14:09
 */
@Configuration
@Slf4j
public class OtherConfiguration {

	//Aop注入IoC
	@Bean
	AopValidator getAopValidator(){
		return new AopValidator();
	}
	//注入全局异常处理
	@Bean
	ExceptionHandlerConfig getExceptionHandlerConfig(){
		return new ExceptionHandlerConfig();
	}
@Bean
	AuthorityExceptionHandlerConfig getAuthorityExceptionHandlerConfig(){
		return new AuthorityExceptionHandlerConfig();
	}



}
