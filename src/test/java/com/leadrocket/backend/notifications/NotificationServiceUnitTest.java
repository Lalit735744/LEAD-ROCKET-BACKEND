package com.leadrocket.backend.notifications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceUnitTest {

    private TenantNotificationRepository repo;
    private SseEmittersManager manager;
    private NotificationService svc;

    @BeforeEach
    void setUp() {
        repo = mock(TenantNotificationRepository.class);
        manager = mock(SseEmittersManager.class);
        svc = new NotificationService(repo, manager);
    }

    @Test
    void send_savesAndPushes() {
        when(repo.save(any(), any())).thenAnswer(inv -> inv.getArgument(1));
        svc.send("c1", "u1", "u2", "T", "Title", "Body");
        verify(repo, times(1)).save(eq("c1"), any());
        verify(manager, times(1)).send(eq("c1"), eq("u2"), any());
    }
}

