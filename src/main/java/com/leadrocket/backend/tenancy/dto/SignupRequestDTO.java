// DTO used for company signup

package com.leadrocket.backend.tenancy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupRequestDTO {

    @NotBlank
    private String companyName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String mobile;

    @NotBlank
    private String ceoName;

    @NotBlank
    private String ceoPassword;

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getCeoName() { return ceoName; }
    public void setCeoName(String ceoName) { this.ceoName = ceoName; }

    public String getCeoPassword() { return ceoPassword; }
    public void setCeoPassword(String ceoPassword) { this.ceoPassword = ceoPassword; }
}
