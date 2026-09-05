package com.college.tenantbilling.Controller;

import com.college.tenantbilling.DTO.UsageRecordRequest;
import com.college.tenantbilling.Model.UsageRecord;
import com.college.tenantbilling.Service.UsageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage")
public class UsageController {

    @Autowired
    private UsageService usageService;

    @PostMapping("/{subscriptionId}")
    @PreAuthorize("hasAnyRole('USER', 'TENANT_ADMIN')")
    public ResponseEntity<UsageRecord> logUsage(
            @PathVariable Long subscriptionId,
            @Valid @RequestBody UsageRecordRequest request) {
        return ResponseEntity.ok(usageService.logUsage(subscriptionId, request));
    }

    @GetMapping("/{subscriptionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UsageRecord>> getUsage(@PathVariable Long subscriptionId) {
        return ResponseEntity.ok(usageService.getUsageForSubscription(subscriptionId));
    }
}