package com.semicolon.ecommerceTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.semicolon.ecommerceTask", "com.semicolon.ecommerceTask.infrastructure.adapter.configuration.superAdminProperties"})
public class EcommerceTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceTaskApplication.class, args);
	}
}
