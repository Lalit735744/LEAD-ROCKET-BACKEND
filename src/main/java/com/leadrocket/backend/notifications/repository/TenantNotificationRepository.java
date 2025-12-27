// Tenant-aware repository interface for notifications

package com.leadrocket.backend.notifications.repository;

import com.leadrocket.backend.notifications.model.Notification;

import java.util.List;
import java.util.Optional;

public interface TenantNotificationRepository {

    Notification save(String companyId, Notification notification);

    Optional<Notification> findById(String companyId, String id);

    List<Notification> findByUser(String companyId, String userId, int limit);
}
