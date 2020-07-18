package com.example.SpringBootApps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.config.APICacheConfig;
import com.example.config.SwaggerConfig;
import com.example.controller.EmployeeController;

@SpringBootApplication(scanBasePackages = "com.example.*")
@EnableJpaRepositories(basePackages = "com.example.*")
@ComponentScan(basePackageClasses = {EmployeeController.class,MyController.class,APICacheConfig.class,SwaggerConfig.class},basePackages = "{com.example.config,com.example.controller,com.example.service,com.example.repository}")
@EnableCaching
@EntityScan(basePackages = "com.example.*") 
public class SpringBootAppsApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(SpringBootAppsApplication.class, args);
	}

}
