package com.elegant.elegantmarket.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.elegant.elegantmarket.security.mapper")
@SpringBootApplication
@EnableEurekaClient
public class ElegantMarketSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElegantMarketSecurityApplication.class, args);
    }

}
