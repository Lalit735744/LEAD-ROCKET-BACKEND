package com.leadrocket.backend.tenancy;

import com.leadrocket.backend.tenancy.dto.SignupRequestDTO;
import com.leadrocket.backend.tenancy.dto.SignupResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignupControllerUnitTest {

    @Test
    void signup_returnsResponseFromService() {
        SignupService svc = mock(SignupService.class);
        SignupController ctrl = new SignupController(svc);

        SignupRequestDTO req = new SignupRequestDTO();
        req.setCompanyName("Acme");
        SignupResponseDTO resp = new SignupResponseDTO();
        resp.setCompanyId("c1");
        when(svc.signup(req)).thenReturn(resp);

        ResponseEntity<SignupResponseDTO> r = ctrl.signup(req);
        assertEquals(200, r.getStatusCode().value());
        SignupResponseDTO body = r.getBody();
        assertNotNull(body);
        assertEquals("c1", body.getCompanyId());
    }
}
