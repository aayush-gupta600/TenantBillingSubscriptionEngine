package com.college.tenantbilling.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterTenantRequest {

    @NotBlank
    private String tenantName;

    @NotBlank
    @Email
    private String adminEmail;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String adminPassword;

    public String getTenantName() { return tenantName; }
    public void setTenantName(String tenantName) { this.tenantName = tenantName; }

    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
}