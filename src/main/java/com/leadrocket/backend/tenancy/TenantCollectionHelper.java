package com.leadrocket.backend.tenancy;

import org.springframework.stereotype.Component;

/**
 * Helper to produce tenant-scoped collection names.
 */
@Component
public class TenantCollectionHelper {

    public String usersCollection(String companyId) {
        return String.format("company_%s_users", companyId);
    }

    public String leadsCollection(String companyId) {
        return String.format("company_%s_leads", companyId);
    }

    public String notificationsCollection(String companyId) {
        return String.format("company_%s_notifications", companyId);
    }

    public String rolesCollection(String companyId) {
        return String.format("company_%s_roles", companyId);
    }

}

