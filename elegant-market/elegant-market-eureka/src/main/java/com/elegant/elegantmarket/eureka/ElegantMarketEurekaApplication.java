package com.elegant.elegantmarket.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ElegantMarketEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElegantMarketEurekaApplication.class, args);
    }

}
