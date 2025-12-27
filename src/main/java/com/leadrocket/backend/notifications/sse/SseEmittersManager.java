// Manages Server-Sent Event connections per tenant & user
// Used for real-time in-app notifications

package com.leadrocket.backend.notifications.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmittersManager {

    // companyId -> (userId -> emitter)
    private final Map<String, Map<String, SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String companyId, String userId) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitters
                .computeIfAbsent(companyId, k -> new ConcurrentHashMap<>())
                .put(userId, emitter);

        emitter.onCompletion(() -> remove(companyId, userId));
        emitter.onTimeout(() -> remove(companyId, userId));
        emitter.onError(e -> remove(companyId, userId));

        return emitter;
    }

    public void send(String companyId, String userId, Object payload) {
        Map<String, SseEmitter> userEmitters = emitters.get(companyId);
        if (userEmitters == null) return;

        SseEmitter emitter = userEmitters.get(userId);
        if (emitter == null) return;

        try {
            emitter.send(SseEmitter.event()
                    .name("notification")
                    .data(payload));
        } catch (IOException ex) {
            remove(companyId, userId);
        }
    }

    private void remove(String companyId, String userId) {
        Map<String, SseEmitter> userEmitters = emitters.get(companyId);
        if (userEmitters != null) {
            userEmitters.remove(userId);
        }
    }
}
