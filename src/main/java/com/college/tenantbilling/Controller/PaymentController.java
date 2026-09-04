package com.college.tenantbilling.Controller;

import com.college.tenantbilling.Model.Payment;
import com.college.tenantbilling.Service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-intent/{invoiceId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Payment> createPaymentIntent(@PathVariable Long invoiceId) throws StripeException {
        return ResponseEntity.ok(paymentService.createPaymentIntent(invoiceId));
    }

    @PostMapping("/{paymentId}/confirm")
    @PreAuthorize("hasAnyRole('ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Payment> confirmPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.markPaymentSucceeded(paymentId));
    }
}