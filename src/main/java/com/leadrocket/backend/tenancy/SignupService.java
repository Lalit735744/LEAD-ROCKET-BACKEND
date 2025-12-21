package com.leadrocket.backend.tenancy;

import com.leadrocket.backend.tenancy.dto.SignupRequestDTO;
import com.leadrocket.backend.tenancy.dto.SignupResponseDTO;
import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import com.leadrocket.backend.users.repository.UserRepository;
import com.leadrocket.backend.security.jwt.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SignupService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository; // global users fallback
    private final TenantUserRepository tenantUserRepository;
    private final TenantRoleRepository tenantRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public SignupService(CompanyRepository companyRepository,
                         UserRepository userRepository,
                         TenantUserRepository tenantUserRepository,
                         TenantRoleRepository tenantRoleRepository,
                         PasswordEncoder passwordEncoder,
                         TokenService tokenService) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.tenantUserRepository = tenantUserRepository;
        this.tenantRoleRepository = tenantRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    /**
     * Create a new company and an admin (CEO) user inside tenant collection, then return JWT.
     */
    public SignupResponseDTO signup(SignupRequestDTO req) {
        Company company = new Company();
        company.setName(req.getCompanyName());
        company.setDomain(req.getDomain());
        company.setCreatedAt(Instant.now());
        company = companyRepository.save(company);

        // create default CEO role for the company
        Role ceo = new Role();
        ceo.setName("CEO");
        ceo.setPermissions(List.of("ALL"));
        Role savedRole = tenantRoleRepository.save(company.getId(), ceo);

        // create admin user inside tenant collection for this company
        User admin = new User();
        admin.setName(req.getAdminName());
        admin.setEmail(req.getAdminEmail());
        admin.setActive(true);
        admin.setPassword(passwordEncoder.encode(req.getAdminPassword()));
        admin.setPasswordChangedAt(Instant.now());
        admin.setCreatedAt(Instant.now());
        admin.setUpdatedAt(Instant.now());
        // set assigned role to the created CEO role
        admin.setRoleIds(List.of(savedRole.getId()));
        User saved = tenantUserRepository.save(company.getId(), admin);

        // generate token embedding company id
        String token = tokenService.generateAccessToken(saved.getId(), company.getId());

        SignupResponseDTO resp = new SignupResponseDTO();
        resp.setCompanyId(company.getId());
        resp.setAdminUserId(saved.getId());
        resp.setAccessToken(token);
        return resp;
    }
}
