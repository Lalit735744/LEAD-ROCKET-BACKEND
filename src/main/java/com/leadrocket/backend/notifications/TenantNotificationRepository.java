package com.leadrocket.backend.notifications;

import java.util.List;
import java.util.Optional;

public interface TenantNotificationRepository {
    Notification save(String companyId, Notification n);
    List<Notification> findByToUserId(String companyId, String toUserId, int limit);
    Optional<Notification> findById(String companyId, String id);
}

