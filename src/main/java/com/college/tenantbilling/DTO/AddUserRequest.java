package com.college.tenantbilling.DTO;

import com.college.tenantbilling.Model.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddUserRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private RoleType role;   // typically USER, but could allow another TENANT_ADMIN

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public RoleType getRole() { return role; }
    public void setRole(RoleType role) { this.role = role; }
}