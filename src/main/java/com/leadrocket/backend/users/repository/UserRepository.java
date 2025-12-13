package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
