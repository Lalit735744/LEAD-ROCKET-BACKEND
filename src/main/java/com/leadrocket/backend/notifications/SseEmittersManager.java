package com.leadrocket.backend.notifications;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple SSE emitter manager keyed by companyId:userId to support in-app push notifications.
 */
@Component
public class SseEmittersManager {

    // map companyId -> (userId -> emitter)
    private final Map<String, Map<String, SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter create(String companyId, String userId) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitters.computeIfAbsent(companyId, k -> new ConcurrentHashMap<>()).put(userId, emitter);
        emitter.onCompletion(() -> remove(companyId, userId));
        emitter.onTimeout(() -> remove(companyId, userId));
        emitter.onError((e) -> remove(companyId, userId));
        return emitter;
    }

    public void remove(String companyId, String userId) {
        Map<String, SseEmitter> m = emitters.get(companyId);
        if (m != null) {
            m.remove(userId);
        }
    }

    public void send(String companyId, String userId, Object event) {
        Map<String, SseEmitter> m = emitters.get(companyId);
        if (m == null) return;
        SseEmitter e = m.get(userId);
        if (e == null) return;
        try {
            e.send(SseEmitter.event().name("notification").data(event));
        } catch (IOException ex) {
            remove(companyId, userId);
        }
    }
}

