// DTO returned after successful signup

package com.leadrocket.backend.tenancy.dto;

public class SignupResponseDTO {

    private String companyId;
    private String message;

    public SignupResponseDTO(String companyId, String message) {
        this.companyId = companyId;
        this.message = message;
    }

    public String getCompanyId() { return companyId; }
    public String getMessage() { return message; }
}
