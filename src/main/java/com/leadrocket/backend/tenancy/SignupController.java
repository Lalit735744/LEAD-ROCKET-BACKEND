package com.leadrocket.backend.tenancy;

import com.leadrocket.backend.tenancy.dto.SignupRequestDTO;
import com.leadrocket.backend.tenancy.dto.SignupResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO req) {
        SignupResponseDTO resp = signupService.signup(req);
        return ResponseEntity.ok(resp);
    }
}

