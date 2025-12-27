// DTO returned for dashboard-level reports

package com.leadrocket.backend.reports.dto;

import java.util.List;

public class DashboardReportDTO {

    private String title;
    private List<ReportMetricDTO> leadStatusSummary;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<ReportMetricDTO> getLeadStatusSummary() {
        return leadStatusSummary;
    }

    public void setLeadStatusSummary(List<ReportMetricDTO> leadStatusSummary) {
        this.leadStatusSummary = leadStatusSummary;
    }
}
