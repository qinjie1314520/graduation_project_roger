package com.authority;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author roger
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.authority.mapper")
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }

}

