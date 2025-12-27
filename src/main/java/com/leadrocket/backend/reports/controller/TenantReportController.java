// Tenant-scoped APIs for reports and analytics

package com.leadrocket.backend.reports.controller;

import com.leadrocket.backend.reports.dto.DashboardReportDTO;
import com.leadrocket.backend.reports.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{companyId}/reports")
public class TenantReportController {

    private final ReportService reportService;

    public TenantReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Dashboard report API
     */
    @GetMapping("/dashboard")
    public DashboardReportDTO dashboard(@PathVariable String companyId) {
        return reportService.getDashboardReport(companyId);
    }
}
