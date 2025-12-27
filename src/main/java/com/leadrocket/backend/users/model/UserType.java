// Defines whether a user is platform-level or tenant-level

package com.leadrocket.backend.users.model;

public enum UserType {
    PLATFORM,   // Lifetime, non-billable, internal
    TENANT     // Company user (trial / paid)
}
