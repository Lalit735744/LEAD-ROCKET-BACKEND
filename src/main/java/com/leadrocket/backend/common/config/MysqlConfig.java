package com.leadrocket.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.leadrocket.backend")
public class MysqlConfig {
	// enter mysql url, username, password in application.yml
}
