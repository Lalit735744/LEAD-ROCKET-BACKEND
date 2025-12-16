package com.leadrocket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LeadRocketBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadRocketBackendApplication.class, args);
	}
}
