package com.leadrocket.backend.security.repository;

import com.leadrocket.backend.users.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission, String> {
}

