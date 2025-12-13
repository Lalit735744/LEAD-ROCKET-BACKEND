package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
