package com.leadrocket.backend.tenancy;

import com.leadrocket.backend.tenancy.dto.SignupRequestDTO;
import com.leadrocket.backend.tenancy.dto.SignupResponseDTO;
import com.leadrocket.backend.users.model.User;
import com.leadrocket.backend.users.model.Role;
import com.leadrocket.backend.users.repository.TenantRoleRepository;
import com.leadrocket.backend.users.repository.TenantUserRepository;
import com.leadrocket.backend.security.jwt.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupServiceUnitTest {

    private CompanyRepository companyRepository;
    private TenantUserRepository tenantUserRepository;
    private TenantRoleRepository tenantRoleRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private SignupService signupService;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        tenantUserRepository = mock(TenantUserRepository.class);
        tenantRoleRepository = mock(TenantRoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        tokenService = mock(TokenService.class);
        signupService = new SignupService(companyRepository, null, tenantUserRepository, tenantRoleRepository, passwordEncoder, tokenService);
    }

    @Test
    void signup_createsCompanyAndAdminAndReturnsToken() {
        SignupRequestDTO req = new SignupRequestDTO();
        req.setCompanyName("Acme");
        req.setDomain("acme.local");
        req.setAdminName("Alice");
        req.setAdminEmail("alice@acme.local");
        req.setAdminPassword("pass");

        // mock company save to return company with id
        Company savedCompany = new Company();
        savedCompany.setId("company-1");
        when(companyRepository.save(any())).thenReturn(savedCompany);

        Role savedRole = new Role();
        savedRole.setId("role-1");
        savedRole.setName("CEO");
        when(tenantRoleRepository.save(any(), any())).thenReturn(savedRole);

        User savedUser = new User();
        savedUser.setId("user-1");
        savedUser.setEmail("alice@acme.local");
        when(tenantUserRepository.save(eq("company-1"), any())).thenReturn(savedUser);

        when(tokenService.generateAccessToken("user-1", "company-1")).thenReturn("token-abc");

        SignupResponseDTO resp = signupService.signup(req);

        assertNotNull(resp);
        assertEquals("company-1", resp.getCompanyId());
        assertEquals("user-1", resp.getAdminUserId());
        assertEquals("token-abc", resp.getAccessToken());

        // verify password was encoded
        verify(passwordEncoder).encode("pass");
    }
}
