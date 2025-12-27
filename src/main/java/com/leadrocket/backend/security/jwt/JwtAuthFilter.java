// Extracts JWT, validates it, and sets AuthContext

package com.leadrocket.backend.security.jwt;

import com.leadrocket.backend.security.context.AuthContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtValidator validator;

    public JwtAuthFilter(JwtValidator validator) {
        this.validator = validator;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = validator.resolveToken(request);

            if (token != null && validator.validate(token)) {
                String userId = validator.getUserId(token);
                String companyId = validator.getCompanyId(token);

                AuthContext.set(new AuthSession(userId, companyId));
            }

            filterChain.doFilter(request, response);
        } finally {
            // VERY IMPORTANT: prevent memory leaks
            AuthContext.clear();
        }
    }
}
