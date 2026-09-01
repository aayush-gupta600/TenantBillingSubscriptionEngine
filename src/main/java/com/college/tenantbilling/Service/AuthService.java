package com.college.tenantbilling.Service;

import com.college.tenantbilling.DTO.*;
import com.college.tenantbilling.Model.RoleType;
import com.college.tenantbilling.Model.Tenant;
import com.college.tenantbilling.Model.User;
import com.college.tenantbilling.Repo.TenantRepo;
import com.college.tenantbilling.Repo.UserRepo;
import com.college.tenantbilling.Security.JwtUtils;
import com.college.tenantbilling.Security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private TenantRepo tenantRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    public MessageResponse registerTenant(RegisterTenantRequest request) {
        if (userRepository.existsByEmail(request.getAdminEmail())) {
            throw new IllegalArgumentException("Email is already in use: " + request.getAdminEmail());
        }
        Tenant tenant = new Tenant(request.getTenantName());
        tenant = tenantRepository.save(tenant);
        User admin = new User(
                tenant,
                request.getAdminEmail(),
                passwordEncoder.encode(request.getAdminPassword()),
                RoleType.TENANT_ADMIN
        );
        userRepository.save(admin);
        return new MessageResponse("Tenant '" + tenant.getName() + "' registered successfully");
    }

    public MessageResponse addUserToTenant(Long tenantId, AddUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already in use: " + request.getEmail());
        }

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + tenantId));

        User user = new User(
                tenant,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole()
        );
        userRepository.save(user);

        return new MessageResponse("User '" + user.getEmail() + "' added to tenant");
    }
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);

        String role = userDetails.getAuthorities().iterator().next()
                .getAuthority()
                .replace("ROLE_", "");
        return new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getTenantId(),
                userDetails.getUsername(),
                role
        );
    }
}