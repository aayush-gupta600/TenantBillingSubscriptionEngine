package com.college.tenantbilling.Controller;

import com.college.tenantbilling.DTO.SubscriptionRequest;
import com.college.tenantbilling.Model.Subscription;
import com.college.tenantbilling.Security.UserDetailsImpl;
import com.college.tenantbilling.Service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public ResponseEntity<Subscription> subscribe(
            @Valid @RequestBody SubscriptionRequest request,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(subscriptionService.subscribe(currentUser.getTenantId(), request));
    }

    @PutMapping("/{id}/change-plan")
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public ResponseEntity<Subscription> changePlan(
            @PathVariable Long id,
            @Valid @RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.changePlan(id, request));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('TENANT_ADMIN')")
    public ResponseEntity<Subscription> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.cancelSubscription(id));
    }

    @GetMapping("/my-tenant")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Subscription>> getMyTenantSubscriptions(
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByTenant(currentUser.getTenantId()));
    }
}