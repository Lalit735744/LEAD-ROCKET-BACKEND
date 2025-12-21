package com.leadrocket.backend.reports;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<ReportRecord, String> {
    List<ReportRecord> findByCompanyId(String companyId);
}

