package com.college.tenantbilling.Controller;

import com.college.tenantbilling.Model.Invoice;
import com.college.tenantbilling.Security.UserDetailsImpl;
import com.college.tenantbilling.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate/{subscriptionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Invoice> generateInvoice(@PathVariable Long subscriptionId) {
        return ResponseEntity.ok(invoiceService.generateInvoice(subscriptionId));
    }

    @GetMapping("/subscription/{subscriptionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Invoice>> getInvoicesForSubscription(@PathVariable Long subscriptionId) {
        return ResponseEntity.ok(invoiceService.getInvoicesForSubscription(subscriptionId));
    }

    @GetMapping("/my-tenant")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Invoice>> getMyTenantInvoices(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return ResponseEntity.ok(invoiceService.getInvoicesForTenant(currentUser.getTenantId()));
    }
}