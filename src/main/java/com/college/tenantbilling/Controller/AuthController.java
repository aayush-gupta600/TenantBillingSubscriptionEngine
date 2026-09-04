package com.college.tenantbilling.Controller;

import com.college.tenantbilling.DTO.*;
import com.college.tenantbilling.Security.UserDetailsImpl;
import com.college.tenantbilling.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register-tenant")
    public ResponseEntity<MessageResponse> registerTenant(@Valid @RequestBody RegisterTenantRequest request) {
        return ResponseEntity.ok(authService.registerTenant(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/add-user")
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public ResponseEntity<MessageResponse> addUser(
            @Valid @RequestBody AddUserRequest request,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(authService.addUserToTenant(currentUser.getTenantId(), request));
    }
}