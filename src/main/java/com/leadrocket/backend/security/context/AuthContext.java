package com.leadrocket.backend.security.context;

import com.leadrocket.backend.security.authkey.AuthSession;

public class AuthContext {

    private static final ThreadLocal<AuthSession> CONTEXT = new ThreadLocal<>();

    public static void set(AuthSession session) {
        CONTEXT.set(session);
    }

    public static AuthSession get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
