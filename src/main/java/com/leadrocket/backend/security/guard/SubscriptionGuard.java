// Blocks tenant users if trial or subscription expired

package com.leadrocket.backend.security.guard;

import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.model.UserType;
import com.leadrocket.backend.tenancy.model.Company;
import com.leadrocket.backend.tenancy.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SubscriptionGuard {

    private final CompanyRepository companyRepository;

    public SubscriptionGuard(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void check(User user) {
        if (user.getUserType() == UserType.PLATFORM) {
            return; // lifetime access
        }

        Company company = companyRepository.findById(user.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (company.getTrialEndsAt() != null &&
                Instant.now().isAfter(company.getTrialEndsAt())) {
            throw new RuntimeException("Trial expired");
        }
    }
}
