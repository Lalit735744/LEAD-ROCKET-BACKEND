package com.leadrocket.backend.security.guard;

// What this file is for:
// Guards tenant routes by ensuring authenticated user belongs to tenant

import com.leadrocket.backend.security.context.AuthContext;
import com.leadrocket.backend.security.authkey.AuthSession;
import org.springframework.stereotype.Component;

@Component
public class TenantGuard {

    // Validate tenant access
    public void check(String companyId) {
        AuthSession session = AuthContext.get();

        if (session == null || !companyId.equals(session.getCompanyId())) {
            throw new RuntimeException("Tenant access denied");
        }
    }
}
