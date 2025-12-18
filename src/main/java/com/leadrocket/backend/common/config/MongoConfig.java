package com.leadrocket.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.leadrocket.backend")
public class MongoConfig {
	// mongodb host, port, db name in application.yml
}
