// Tenant-aware repository for users

package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.User;

import java.util.List;
import java.util.Optional;

public interface TenantUserRepository {

    User save(String companyId, User user);

    Optional<User> findById(String companyId, String userId);

    Optional<User> findByEmail(String companyId, String email);

    List<User> findAll(String companyId);
}
