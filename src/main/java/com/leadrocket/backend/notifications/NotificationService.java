package com.leadrocket.backend.notifications;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final TenantNotificationRepository repository;
    private final SseEmittersManager sseEmittersManager;

    public NotificationService(TenantNotificationRepository repository, SseEmittersManager sseEmittersManager) {
        this.repository = repository;
        this.sseEmittersManager = sseEmittersManager;
    }

    public Notification send(String companyId, String fromUserId, String toUserId, String type, String title, String body) {
        Notification n = new Notification();
        n.setFromUserId(fromUserId);
        n.setToUserId(toUserId);
        n.setType(type);
        n.setTitle(title);
        n.setBody(body);
        Notification saved = repository.save(companyId, n);
        // push to SSE subscribers if any
        sseEmittersManager.send(companyId, toUserId, saved);
        return saved;
    }

    public List<Notification> listForUser(String companyId, String userId, int limit) {
        return repository.findByToUserId(companyId, userId, limit);
    }

    public Notification markRead(String companyId, String id) {
        Notification n = repository.findById(companyId, id).orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        return repository.save(companyId, n);
    }
}
