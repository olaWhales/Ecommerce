package com.semicolon.ecommerceTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secret.properties")
public class EcommerceTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceTaskApplication.class, args);
	}
}


