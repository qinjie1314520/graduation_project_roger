package com.admin.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.controller.config.ExceptionHandlerConfig;
import com.controller.validator.AopValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 其他的需要注入aop容器的Bean
 * @author roger
 * @since  2020/11/3 14:09
 */
@Configuration
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
	//解决分页total为0的问题
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}
}
