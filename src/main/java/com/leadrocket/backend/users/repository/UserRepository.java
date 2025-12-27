// UserRepository
// Repository for PLATFORM users (non-tenant, lifetime users)
// These users are not tied to any company and are not billed

package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a platform user by email.
     * Used for login and bootstrap checks.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user already exists with given email.
     * Used during platform bootstrap.
     */
    boolean existsByEmail(String email);
}
