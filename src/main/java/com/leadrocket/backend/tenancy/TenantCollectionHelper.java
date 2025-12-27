package com.leadrocket.backend.tenancy;

import org.springframework.stereotype.Component;

@Component
public class TenantCollectionHelper {

    public String usersCollection(String companyId) {
        return "company_" + companyId + "_users";
    }

    public String rolesCollection(String companyId) {
        return "company_" + companyId + "_roles";
    }

    public String leadsCollection(String companyId) {
        return "company_" + companyId + "_leads";
    }

    public String activitiesCollection(String companyId) {
        return "company_" + companyId + "_activities";
    }

    public String teamsCollection(String companyId) {
        return "company_" + companyId + "_teams";
    }

    public String notificationsCollection(String companyId) {
        return "company_" + companyId + "_notifications";
    }
}
