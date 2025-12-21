package com.leadrocket.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration. Exposes a SecurityFilterChain that:
 * - allows unauthenticated access to registration endpoints
 * - requires authentication for other endpoints
 */
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // API clients; enable if you need CSRF protection
            .authorizeHttpRequests(auth -> auth
                // allow registration endpoints across potential API versions
                .requestMatchers(HttpMethod.POST, "/api/users", "/api/v1/users", "/api/**/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users", "/api/v1/users", "/api/**/users").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
