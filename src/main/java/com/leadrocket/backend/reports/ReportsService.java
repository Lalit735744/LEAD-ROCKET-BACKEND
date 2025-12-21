package com.leadrocket.backend.reports;

import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportsService {

    private final MongoTemplate mongoTemplate;
    private final TenantCollectionHelper helper;

    public ReportsService(MongoTemplate mongoTemplate, TenantCollectionHelper helper) {
        this.mongoTemplate = mongoTemplate;
        this.helper = helper;
    }

    /**
     * Simple aggregation: group leads by assignedTo and count them within date range.
     */
    public List<Document> employeeLeadCounts(String companyId, Date start, Date end) {
        String col = helper.leadsCollection(companyId);
        MatchOperation match = Aggregation.match(Criteria.where("createdAt").gte(start).lte(end));
        GroupOperation group = Aggregation.group("assignedTo")
                .count().as("leadCount")
                .sum(ConditionalOperators.when(Criteria.where("status").is("CONVERTED")).then(1).otherwise(0)).as("convertedCount");

        Aggregation agg = Aggregation.newAggregation(match, group);
        AggregationResults<Document> results = mongoTemplate.aggregate(agg, col, Document.class);
        return results.getMappedResults();
    }
}
