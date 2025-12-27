// Tenant-aware repository for activities

package com.leadrocket.backend.activities.repository;

import com.leadrocket.backend.activities.model.Activity;

import java.util.List;
import java.util.Optional;

public interface TenantActivityRepository {

    Activity save(String companyId, Activity activity);

    Optional<Activity> findById(String companyId, String id);

    List<Activity> findByLead(String companyId, String leadId);

    List<Activity> findByUser(String companyId, String userId);

    List<Activity> findPendingByUser(String companyId, String userId);

    List<Activity> findOverdueByUser(String companyId, String userId);
}
