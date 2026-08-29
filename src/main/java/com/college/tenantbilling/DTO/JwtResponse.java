package com.college.tenantbilling.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private final String type = "Bearer";
    private Long userId;
    private Long tenantId;
    private String email;
    private String role;

    public JwtResponse(String token, Long userId, Long tenantId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.tenantId = tenantId;
        this.email = email;
        this.role = role;
    }
}