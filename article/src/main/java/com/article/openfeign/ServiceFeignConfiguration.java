package com.article.openfeign;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置
 *
 * @author roger
 */
@Configuration
public class ServiceFeignConfiguration {

    @Value("${service.feign.connectTimeout}")
    private int connectTimeout;

    @Value("${service.feign.readTimeOut}")
    private int readTimeout;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeout, readTimeout);
    }
}