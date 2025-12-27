// Guards tenant access across controllers and services

package com.leadrocket.backend.security.guard;

import com.leadrocket.backend.common.exception.ForbiddenException;
import com.leadrocket.backend.security.context.AuthContext;

public final class TenantGuard {

    private TenantGuard() {}

    public static void verifyCompany(String pathCompanyId) {

        AuthSession session = AuthContext.get();
        if (session == null) {
            throw new ForbiddenException("Unauthenticated");
        }

        if (!pathCompanyId.equals(session.getCompanyId())) {
            throw new ForbiddenException("Cross-tenant access denied");
        }
    }
}
