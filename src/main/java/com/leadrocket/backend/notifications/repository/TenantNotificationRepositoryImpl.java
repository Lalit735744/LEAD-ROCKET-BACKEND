// MongoTemplate implementation for tenant notifications

package com.leadrocket.backend.notifications.repository;

import com.leadrocket.backend.notifications.model.Notification;
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

    public TenantNotificationRepositoryImpl(MongoTemplate mongoTemplate,
                                            TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    @Override
    public Notification save(String companyId, Notification notification) {
        return mongoTemplate.save(notification, helper.notificationsCollection(companyId));
    }

    @Override
    public Optional<Notification> findById(String companyId, String id) {
        return Optional.ofNullable(
                mongoTemplate.findById(id, Notification.class,
                        helper.notificationsCollection(companyId))
        );
    }

    @Override
    public List<Notification> findByUser(String companyId, String userId, int limit) {
        Query q = new Query(Criteria.where("toUserId").is(userId));
        q.limit(limit);
        return mongoTemplate.find(q, Notification.class,
                helper.notificationsCollection(companyId));
    }
}
