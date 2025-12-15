package com.leadrocket.backend.reports.aggregation;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;

@Component
public class LeadReportAggregation {

	private final MongoTemplate template;

	public LeadReportAggregation(MongoTemplate template) {
		this.template = template;
	}

	public Object leadsByStatus() {
		Aggregation agg = Aggregation.newAggregation(
				Aggregation.group("status").count().as("count")
		);
		return template.aggregate(agg, "leads", Object.class).getMappedResults();
	}
}
