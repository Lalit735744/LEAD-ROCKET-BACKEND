// Mongo configuration
// Enables auditing and keeps Mongo setup explicit

package com.leadrocket.backend.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // Mongo auditing enabled (createdAt, updatedAt)
}
