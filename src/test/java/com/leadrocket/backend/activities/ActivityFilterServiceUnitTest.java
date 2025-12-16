package com.leadrocket.backend.activities;

import com.leadrocket.backend.activities.filter.ActivityFilterService;
import com.leadrocket.backend.activities.model.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ActivityFilterServiceUnitTest {

    private MongoTemplate mongoTemplate;
    private ActivityFilterService filterService;

    @BeforeEach
    void setup() {
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        // we pass null as ActivityService because it's only used by DTO mapping methods
        filterService = new ActivityFilterService(mongoTemplate, null);
    }

    @Test
    void testFilterByMetadata() {
        Activity a = new Activity();
        a.setId("1");
        when(mongoTemplate.find(any(Query.class), Mockito.eq(Activity.class))).thenReturn(List.of(a));

        var results = filterService.filterByMetadata("foo", "bar");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getId());
    }
}

