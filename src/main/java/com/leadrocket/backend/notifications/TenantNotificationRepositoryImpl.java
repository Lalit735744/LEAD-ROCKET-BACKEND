package com.leadrocket.backend.notifications;

import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TenantNotificationRepositoryImpl implements TenantNotificationRepository {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public TenantNotificationRepositoryImpl(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public Notification save(String companyId, Notification n) {
        return mongoTemplate.save(n, helper.notificationsCollection(companyId));
    }

    @Override
    public List<Notification> findByToUserId(String companyId, String toUserId, int limit) {
        Query q = new Query(Criteria.where("toUserId").is(toUserId));
        q.limit(limit);
        return mongoTemplate.find(q, Notification.class, helper.notificationsCollection(companyId));
    }

    @Override
    public Optional<Notification> findById(String companyId, String id) {
        Notification n = mongoTemplate.findById(id, Notification.class, helper.notificationsCollection(companyId));
        return Optional.ofNullable(n);
    }
}

