package com.leadrocket.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for stateless APIs
                .csrf(AbstractHttpConfigurer::disable)

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        /* ================= PUBLIC ENDPOINTS ================= */

                        // User registration endpoints (explicit paths only)
                        .requestMatchers(HttpMethod.POST,
                                "/api/users",
                                "/api/v1/users"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/users",
                                "/api/v1/users"
                        ).permitAll()

                        // Allow browser CORS preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        /* ================= PROTECTED API ================= */

                        // Any other API endpoint requires authentication
                        .requestMatchers("/api/**").authenticated()

                        /* ================= NON-API ================= */

                        // Root, health, actuator, etc.
                        .anyRequest().permitAll()
                )

                // Disable default login mechanisms (JWT/AuthKey handled by filters)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
