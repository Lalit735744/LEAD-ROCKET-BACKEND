// Application entry point
// Bootstraps the LeadRocket backend application
// Scans all modules under com.leadrocket.backend

package com.leadrocket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeadRocketBackendApplication {

	public static void main(String[] args) {

		// Starts Spring Boot
		// - Initializes MongoDB
		// - Loads security filters
		// - Registers controllers, services, repositories
		SpringApplication.run(LeadRocketBackendApplication.class, args);
	}
}
