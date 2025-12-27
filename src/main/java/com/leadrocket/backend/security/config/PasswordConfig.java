package com.leadrocket.backend.security.config;

// What this file is for:
// Provides a PasswordEncoder bean for hashing passwords safely.
// Used across user signup, login, and platform bootstrap.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    // Creates BCrypt password encoder
    // BCrypt is industry standard and safe for production
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
