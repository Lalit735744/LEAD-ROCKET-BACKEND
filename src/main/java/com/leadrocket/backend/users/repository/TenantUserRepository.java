package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Tenant-aware repository for users. All operations require a companyId to determine the
 * correct collection name.
 */
public interface TenantUserRepository {
    User save(String companyId, User user);
    Optional<User> findById(String companyId, String id);
    List<User> findAll(String companyId);
    User findByEmail(String companyId, String email);
    boolean existsByEmail(String companyId, String email);
    long count(String companyId);
}

