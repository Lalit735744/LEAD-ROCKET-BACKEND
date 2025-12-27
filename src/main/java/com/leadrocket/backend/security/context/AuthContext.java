// Holds authenticated request context
// Single source of truth for user + company across the request

package com.leadrocket.backend.security.context;

public final class AuthContext {

    private static final ThreadLocal<AuthSession> CONTEXT = new ThreadLocal<>();

    private AuthContext() {}

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
