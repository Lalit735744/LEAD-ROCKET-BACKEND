// Mongo audit listener
// Automatically sets createdAt / updatedAt

package com.leadrocket.backend.common.audit;

import com.leadrocket.backend.common.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuditEntityListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {

        Object source = event.getSource();

        if (source instanceof BaseEntity entity) {

            Instant now = Instant.now();

            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(now);
                entity.setCreatedBy("SYSTEM");
            }

            entity.setUpdatedAt(now);
            entity.setUpdatedBy("SYSTEM");
        }
    }
}
