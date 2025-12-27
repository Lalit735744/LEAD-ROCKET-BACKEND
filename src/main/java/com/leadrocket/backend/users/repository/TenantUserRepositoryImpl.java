// MongoTemplate implementation for tenant users

package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.tenancy.service.TenantCollectionHelper;
import com.leadrocket.backend.users.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantUserRepositoryImpl implements TenantUserRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantUserRepositoryImpl(MongoTemplate mongoTemplate,
                                    TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public User save(String companyId, User user) {
        return mongoTemplate.save(user, helper.usersCollection(companyId));
    }

    @Override
    public Optional<User> findById(String companyId, String userId) {
        return Optional.ofNullable(
                mongoTemplate.findById(userId, User.class,
                        helper.usersCollection(companyId))
        );
    }

    @Override
    public Optional<User> findByEmail(String companyId, String email) {
        Query q = new Query(Criteria.where("email").is(email));
        return Optional.ofNullable(
                mongoTemplate.findOne(q, User.class,
                        helper.usersCollection(companyId))
        );
    }

    @Override
    public List<User> findAll(String companyId) {
        return mongoTemplate.findAll(
                User.class, helper.usersCollection(companyId)
        );
    }
}
