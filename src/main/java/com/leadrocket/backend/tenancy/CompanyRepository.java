package com.leadrocket.backend.tenancy;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Global repository for companies.
 */
public interface CompanyRepository extends MongoRepository<Company, String> {
    Company findByDomain(String domain);
}

