package com.leadrocket.backend.notifications;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/{companyId}/notifications")
public class NotificationController {

    private final NotificationService service;
    private final SseEmittersManager sseEmittersManager;

    public NotificationController(NotificationService service, SseEmittersManager sseEmittersManager) {
        this.service = service;
        this.sseEmittersManager = sseEmittersManager;
    }

    @PostMapping
    public ResponseEntity<Notification> send(@PathVariable String companyId, @RequestParam String fromUserId, @RequestParam String toUserId,
                                             @RequestParam(required = false) String type,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) String body) {
        Notification n = service.send(companyId, fromUserId, toUserId, type, title, body);
        return ResponseEntity.ok(n);
    }

    @GetMapping
    public ResponseEntity<List<Notification>> list(@PathVariable String companyId, @RequestParam String userId, @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(service.listForUser(companyId, userId, limit));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markRead(@PathVariable String companyId, @PathVariable String id) {
        return ResponseEntity.ok(service.markRead(companyId, id));
    }

    /**
     * Subscribe to in-app notifications via SSE. Connect as /api/{companyId}/notifications/subscribe?userId=XXX
     */
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@PathVariable String companyId, @RequestParam String userId) {
        return sseEmittersManager.create(companyId, userId);
    }
}
