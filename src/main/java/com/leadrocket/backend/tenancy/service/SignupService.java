// Handles company signup and initial tenant bootstrap

package com.leadrocket.backend.tenancy.service;

import com.leadrocket.backend.common.exception.ConflictException;
import com.leadrocket.backend.tenancy.dto.SignupRequestDTO;
import com.leadrocket.backend.tenancy.dto.SignupResponseDTO;
import com.leadrocket.backend.tenancy.model.Company;
import com.leadrocket.backend.tenancy.repository.CompanyRepository;
import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SignupService {

    private final CompanyRepository companyRepository;
    private final TenantUserRepository userRepository;
    private final TenantRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(
            CompanyRepository companyRepository,
            TenantUserRepository userRepository,
            TenantRoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignupResponseDTO signup(SignupRequestDTO dto) {

        // Prevent duplicate companies
        if (companyRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Company email already registered");
        }
        if (companyRepository.existsByMobile(dto.getMobile())) {
            throw new ConflictException("Company mobile already registered");
        }

        // Create company
        Company company = new Company();
        company.setName(dto.getCompanyName());
        company.setEmail(dto.getEmail());
        company.setMobile(dto.getMobile());
        company.setCreatedAt(Instant.now());
        company.setTrialEndsAt(
                Instant.now().plus(30, ChronoUnit.DAYS)
        );

        Company savedCompany = companyRepository.save(company);

        // Create CEO role
        Role ceoRole = new Role();
        ceoRole.setName("CEO");
        ceoRole.setPermissions(List.of("ALL"));
        Role savedRole = roleRepository.save(savedCompany.getId(), ceoRole);

        // Create CEO user
        User ceo = new User();
        ceo.setName(dto.getCeoName());
        ceo.setEmail(dto.getEmail());
        ceo.setMobile(dto.getMobile());
        ceo.setActive(true);
        ceo.setCompanyId(savedCompany.getId());
        ceo.setRoleIds(List.of(savedRole.getId()));
        ceo.setPassword(passwordEncoder.encode(dto.getCeoPassword()));
        ceo.setCreatedAt(Instant.now());
        ceo.setUpdatedAt(Instant.now());

        userRepository.save(savedCompany.getId(), ceo);

        return new SignupResponseDTO(
                savedCompany.getId(),
                "Signup successful. 30-day free trial activated."
        );
    }
}
