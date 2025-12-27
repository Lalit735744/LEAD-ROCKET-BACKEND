// Aggregation queries related to lead reports
// Executes MongoDB aggregations in tenant collections

package com.leadrocket.backend.reports.aggregation;

import com.leadrocket.backend.reports.dto.ReportMetricDTO;
import com.leadrocket.backend.tenancy.TenantCollectionHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeadReportAggregation {

	private final MongoTemplate mongoTemplate;
	private final TenantCollectionHelper helper;

	public LeadReportAggregation(MongoTemplate mongoTemplate,
								 TenantCollectionHelper helper) {
		this.mongoTemplate = mongoTemplate;
		this.helper = helper;
	}

	/**
	 * Group leads by status and return counts.
	 */
	public List<ReportMetricDTO> leadsByStatus(String companyId) {

		Aggregation aggregation = Aggregation.newAggregation(
				Aggregation.group("status").count().as("count"),
				Aggregation.project("count")
						.and("_id").as("key")
		);

		return mongoTemplate.aggregate(
				aggregation,
				helper.leadsCollection(companyId),
				ReportMetricDTO.class
		).getMappedResults();
	}
}
