package com.leadrocket.backend.common.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController
 *
 * Provides lightweight health-check endpoints.
 * These endpoints are useful for:
 *  - Verifying MongoDB connectivity
 *  - EC2 / Load Balancer health checks
 *  - Debugging production issues quickly
 */
@RestController
public class HealthController {

    private final UserRepository userRepository;

    public HealthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Database health check.
     *
     * Performs a simple count query on users collection.
     * If MongoDB is unreachable, this endpoint will fail.
     */
    @GetMapping("/health/db")
    public String databaseHealth() {
        try {
            long count = userRepository.count();
            return "STATUS: OK | MongoDB Connected | users=" + count;
        } catch (Exception ex) {
            return "STATUS: ERROR | MongoDB Failure | " + ex.getMessage();
        }
    }
}
