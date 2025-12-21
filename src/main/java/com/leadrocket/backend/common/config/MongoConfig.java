package com.leadrocket.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Enable Mongo repositories under com.leadrocket.backend. Mongo connection is configured
 * via application-dev.yml (host/port/database) or SPRING_DATA_MONGODB_URI environment variable.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.leadrocket.backend")
public class MongoConfig {
	// mongodb host, port, db name in application.yml
}
