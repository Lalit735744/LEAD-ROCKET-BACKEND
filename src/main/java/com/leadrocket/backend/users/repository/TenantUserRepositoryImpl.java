package com.leadrocket.backend.users.repository;

import com.leadrocket.backend.tenancy.TenantCollectionHelper;
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

    public TenantUserRepositoryImpl(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public User save(String companyId, User user) {
        String col = helper.usersCollection(companyId);
        return mongoTemplate.save(user, col);
    }

    @Override
    public Optional<User> findById(String companyId, String id) {
        String col = helper.usersCollection(companyId);
        User u = mongoTemplate.findById(id, User.class, col);
        return Optional.ofNullable(u);
    }

    @Override
    public List<User> findAll(String companyId) {
        String col = helper.usersCollection(companyId);
        return mongoTemplate.findAll(User.class, col);
    }

    @Override
    public User findByEmail(String companyId, String email) {
        String col = helper.usersCollection(companyId);
        Query q = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(q, User.class, col);
    }

    @Override
    public boolean existsByEmail(String companyId, String email) {
        String col = helper.usersCollection(companyId);
        Query q = new Query(Criteria.where("email").is(email));
        return mongoTemplate.exists(q, col);
    }

    @Override
    public long count(String companyId) {
        String col = helper.usersCollection(companyId);
        return mongoTemplate.getCollection(col).countDocuments();
    }
}

