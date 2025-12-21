package com.leadrocket.backend.common.health;

import com.leadrocket.backend.users.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lightweight health endpoints. Useful to verify external dependencies like MongoDB.
 */
@RestController
public class HealthController {

    private final UserRepository userRepository;

    public HealthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Check Mongo connectivity by performing a simple count query against the users collection.
     * Returns a small text response with the count or an error message.
     */
    @GetMapping("/health/db")
    public String dbHealth() {
        try {
            long count = userRepository.count();
            return "mongo-ok: users=" + count;
        } catch (Exception ex) {
            return "mongo-error: " + ex.getMessage();
        }
    }
}
