// Core notification business logic

package com.leadrocket.backend.notifications.service;

import com.leadrocket.backend.notifications.model.Notification;
import com.leadrocket.backend.notifications.repository.TenantNotificationRepository;
import com.leadrocket.backend.notifications.sse.SseEmittersManager;
import com.leadrocket.backend.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final TenantNotificationRepository repository;
    private final SseEmittersManager sseManager;

    public NotificationService(TenantNotificationRepository repository,
                               SseEmittersManager sseManager) {
        this.repository = repository;
        this.sseManager = sseManager;
    }

    public Notification send(String companyId,
                             String fromUserId,
                             String toUserId,
                             String type,
                             String title,
                             String message) {

        Notification n = new Notification();
        n.setCompanyId(companyId);
        n.setFromUserId(fromUserId);
        n.setToUserId(toUserId);
        n.setType(type);
        n.setTitle(title);
        n.setMessage(message);

        Notification saved = repository.save(companyId, n);

        // Push real-time notification if user is connected
        sseManager.send(companyId, toUserId, saved);

        return saved;
    }

    public List<Notification> getUserNotifications(String companyId,
                                                   String userId,
                                                   int limit) {
        return repository.findByUser(companyId, userId, limit);
    }

    public Notification markAsRead(String companyId, String notificationId) {
        Notification n = repository.findById(companyId, notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));

        n.setRead(true);
        return repository.save(companyId, n);
    }
}
