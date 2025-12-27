// Repository for tenant companies

package com.leadrocket.backend.tenancy.repository;

import com.leadrocket.backend.tenancy.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}
