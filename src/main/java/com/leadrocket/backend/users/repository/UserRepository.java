package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.users.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data repository for User documents.
 *
 * Contains derived query methods used by the service layer.
 */
public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
	boolean existsByEmail(String email);
}
