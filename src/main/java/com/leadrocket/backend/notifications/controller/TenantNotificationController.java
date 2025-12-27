// Tenant-scoped REST APIs for notifications

package com.leadrocket.backend.notifications.controller;

import com.leadrocket.backend.notifications.model.Notification;
import com.leadrocket.backend.notifications.service.NotificationService;
import com.leadrocket.backend.notifications.sse.SseEmittersManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/notifications")
public class TenantNotificationController {

    private final NotificationService service;
    private final SseEmittersManager sseManager;

    public TenantNotificationController(NotificationService service,
                                        SseEmittersManager sseManager) {
        this.service = service;
        this.sseManager = sseManager;
    }

    @GetMapping
    public List<Notification> list(
            @PathVariable String companyId,
            @RequestParam String userId,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return service.getUserNotifications(companyId, userId, limit);
    }

    @PutMapping("/{id}/read")
    public Notification markRead(
            @PathVariable String companyId,
            @PathVariable String id
    ) {
        return service.markAsRead(companyId, id);
    }

    /**
     * SSE endpoint for in-app notifications
     * /api/{companyId}/notifications/subscribe?userId=xxx
     */
    @GetMapping("/subscribe")
    public SseEmitter subscribe(
            @PathVariable String companyId,
            @RequestParam String userId
    ) {
        return sseManager.subscribe(companyId, userId);
    }
}
