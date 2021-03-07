package com.notification.config;


import com.controller.config.ExceptionHandlerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 其他的需要注入aop容器的Bean
 * @author roger
 * @since  2020/11/3 14:09
 */
@Configuration
public class OtherConfiguration {

	//注入全局异常处理
	@Bean
	ExceptionHandlerConfig getExceptionHandlerConfig(){
		return new ExceptionHandlerConfig();
	}
}
