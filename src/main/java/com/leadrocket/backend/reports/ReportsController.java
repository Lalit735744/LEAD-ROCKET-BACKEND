package com.leadrocket.backend.reports;

import org.bson.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/{companyId}/reports")
public class ReportsController {

    private final ReportsService reportsService;
    private final ReportsExportService exportService;
    private final ReportRepository reportRepository;

    public ReportsController(ReportsService reportsService, ReportsExportService exportService, ReportRepository reportRepository) {
        this.reportsService = reportsService;
        this.exportService = exportService;
        this.reportRepository = reportRepository;
    }

    @GetMapping("/employee-leads")
    public ResponseEntity<List<Document>> employeeLeadCounts(@PathVariable String companyId,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        return ResponseEntity.ok(reportsService.employeeLeadCounts(companyId, start, end));
    }

    @GetMapping("/employee-leads/csv")
    public ResponseEntity<byte[]> employeeLeadCountsCsv(@PathVariable String companyId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        List<Document> rows = reportsService.employeeLeadCounts(companyId, start, end);
        // build CSV
        String header = "employeeId,leadCount,convertedCount\n";
        String body = rows.stream().map(d -> {
            String emp = d.getString("_id");
            Object leadCount = d.get("leadCount");
            Object conv = d.get("convertedCount");
            return String.format("%s,%s,%s", emp == null ? "" : emp, leadCount == null ? "0" : leadCount.toString(), conv == null ? "0" : conv.toString());
        }).collect(java.util.stream.Collectors.joining("\n"));
        String csv = header + body;
        byte[] bytes = csv.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_leads.csv");
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @PostMapping("/employee-leads/export")
    public ResponseEntity<ReportRecord> scheduleExport(@PathVariable String companyId,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {
        ReportRecord rec = exportService.scheduleEmployeeLeadExport(companyId, start, end);
        return ResponseEntity.ok(rec);
    }

    @GetMapping("/export/{reportId}")
    public ResponseEntity<byte[]> downloadExport(@PathVariable String companyId, @PathVariable String reportId) {
        Optional<ReportRecord> maybe = reportRepository.findById(reportId);
        if (maybe.isEmpty()) return ResponseEntity.notFound().build();
        ReportRecord rec = maybe.get();
        if (!companyId.equals(rec.getCompanyId())) return ResponseEntity.status(403).build();
        if (!"DONE".equals(rec.getStatus())) {
            return ResponseEntity.status(409).body(("Report status=" + rec.getStatus()).getBytes(StandardCharsets.UTF_8));
        }
        byte[] bytes = rec.getContent() == null ? new byte[0] : rec.getContent().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + rec.getFileName());
        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}
