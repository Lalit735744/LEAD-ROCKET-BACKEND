// Helper for resolving tenant-specific MongoDB collection names
// Keeps all collection naming logic in one place

package com.leadrocket.backend.tenancy.service;

import org.springframework.stereotype.Component;

@Component
public class TenantCollectionHelper {

    public String usersCollection(String companyId) {
        return "company_" + companyId + "_users";
    }

    public String leadsCollection(String companyId) {
        return "company_" + companyId + "_leads";
    }

    public String activitiesCollection(String companyId) {
        return "company_" + companyId + "_activities";
    }

    public String notificationsCollection(String companyId) {
        return "company_" + companyId + "_notifications";
    }

    public String rolesCollection(String companyId) {
        return "company_" + companyId + "_roles";
    }
}
