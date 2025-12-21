package com.leadrocket.backend.reports;

import org.bson.Document;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Background export service that runs report aggregation and stores CSV in `reports` collection.
 */
@Service
public class ReportsExportService {

    private final ReportsService reportsService;
    private final ReportRepository reportRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReportsExportService(ReportsService reportsService, ReportRepository reportRepository) {
        this.reportsService = reportsService;
        this.reportRepository = reportRepository;
    }

    public ReportRecord scheduleEmployeeLeadExport(String companyId, Date start, Date end) {
        ReportRecord rec = new ReportRecord();
        rec.setCompanyId(companyId);
        rec.setType("employee-leads");
        rec.setStatus("PENDING");
        rec.setCreatedAt(new Date());
        rec = reportRepository.save(rec);

        // capture id for background task and avoid capturing mutable rec variable
        final String recId = rec.getId();

        executor.submit(() -> {
            try {
                List<Document> rows = reportsService.employeeLeadCounts(companyId, start, end);
                String header = "employeeId,leadCount,convertedCount\n";
                StringBuilder sb = new StringBuilder(header);
                for (Document d : rows) {
                    String emp = d.getString("_id");
                    Object leadCount = d.get("leadCount");
                    Object conv = d.get("convertedCount");
                    sb.append(String.format("%s,%s,%s\n", emp == null ? "" : emp, leadCount == null ? "0" : leadCount.toString(), conv == null ? "0" : conv.toString()));
                }
                String csv = sb.toString();

                // reload record, update and save
                ReportRecord up = reportRepository.findById(recId).orElse(null);
                if (up != null) {
                    up.setContent(csv);
                    up.setFileName("employee_leads_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv");
                    up.setStatus("DONE");
                    up.setCompletedAt(new Date());
                    reportRepository.save(up);
                }
            } catch (Exception ex) {
                ReportRecord up = reportRepository.findById(recId).orElse(null);
                if (up != null) {
                    up.setStatus("FAILED");
                    up.setErrorMessage(ex.getMessage());
                    up.setCompletedAt(new Date());
                    reportRepository.save(up);
                }
            }
        });

        return rec;
    }
}
