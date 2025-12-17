package com.leadrocket.backend.common.audit;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MongoDB audit listener to populate createdAt/updatedAt and createdBy/updatedBy
 */
@Component
public class AuditEntityListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source instanceof BaseEntity entity) {
            Date now = new Date();
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(now);
                entity.setCreatedBy("SYSTEM"); // TODO: wire real user
            }
            entity.setUpdatedAt(now);
            entity.setUpdatedBy("SYSTEM");
        }
        super.onBeforeConvert(event);
    }
}
