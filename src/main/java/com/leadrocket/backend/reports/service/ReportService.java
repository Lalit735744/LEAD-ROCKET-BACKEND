// Service layer for generating reports
// Orchestrates aggregations and assembles DTOs

package com.leadrocket.backend.reports.service;

import com.leadrocket.backend.reports.aggregation.LeadReportAggregation;
import com.leadrocket.backend.reports.dto.DashboardReportDTO;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

	private final LeadReportAggregation leadAggregation;

	public ReportService(LeadReportAggregation leadAggregation) {
		this.leadAggregation = leadAggregation;
	}

	/**
	 * Dashboard report for company.
	 */
	public DashboardReportDTO getDashboardReport(String companyId) {

		DashboardReportDTO dto = new DashboardReportDTO();
		dto.setTitle("Dashboard Overview");
		dto.setLeadStatusSummary(
				leadAggregation.leadsByStatus(companyId)
		);

		return dto;
	}
}
