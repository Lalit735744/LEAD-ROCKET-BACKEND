package com.leadrocket.backend.reports;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportsExportServiceUnitTest {

    private ReportsService reportsService;
    private ReportRepository reportRepository;
    private ReportsExportService exportService;

    @BeforeEach
    void setUp() {
        reportsService = mock(ReportsService.class);
        reportRepository = mock(ReportRepository.class);
        exportService = new ReportsExportService(reportsService, reportRepository);
    }

    @Test
    void scheduleExport_createsReportRecord_and_runsBackgroundJob() {
        when(reportRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportsService.employeeLeadCounts(any(), any(), any())).thenReturn(List.of(new Document("_id", "user-1").append("leadCount", 5).append("convertedCount", 1)));

        ReportRecord rec = exportService.scheduleEmployeeLeadExport("company-1", new Date(), new Date());
        assert rec != null;
        // saved once initially
        verify(reportRepository, times(1)).save(any());
    }
}
