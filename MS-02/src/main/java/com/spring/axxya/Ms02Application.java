package com.spring.axxya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
public class Ms02Application {
    public static void main(String[] args) {
        SpringApplication.run(Ms02Application.class, args);
    }

}
