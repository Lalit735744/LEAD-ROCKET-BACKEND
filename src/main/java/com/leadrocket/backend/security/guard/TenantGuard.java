package com.leadrocket.backend.security.guard;

import com.leadrocket.backend.security.context.AuthContext;
import com.leadrocket.backend.security.authkey.AuthSession;

// Guards against cross-tenant access
public class TenantGuard {

    public static void checkCompany(String companyId) {

        AuthSession session = AuthContext.get();

        if (session == null || session.getCompanyId() == null) {
            throw new RuntimeException("Unauthenticated request");
        }

        if (!companyId.equals(session.getCompanyId())) {
            throw new RuntimeException("Cross-tenant access denied");
        }
    }
}
