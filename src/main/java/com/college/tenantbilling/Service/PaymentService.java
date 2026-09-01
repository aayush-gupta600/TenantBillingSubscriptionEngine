package com.college.tenantbilling.Service;

import com.college.tenantbilling.Model.Invoice;
import com.college.tenantbilling.Model.Payment;
import com.college.tenantbilling.Repo.InvoiceRepo;
import com.college.tenantbilling.Repo.PaymentRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @Autowired
    private InvoiceRepo invoiceRepository;

    @Autowired
    private PaymentRepo paymentRepository;
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }
    public Payment createPaymentIntent(Long invoiceId) throws StripeException {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
        long amountInCents = invoice.getTotalAmount()
                .multiply(java.math.BigDecimal.valueOf(100))
                .longValueExact();
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .putMetadata("invoiceId", String.valueOf(invoice.getId()))
                .build();
        PaymentIntent intent = PaymentIntent.create(params);
        Payment payment = new Payment(invoice, invoice.getTotalAmount());
        payment.setStripePaymentIntentId(intent.getId());
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }
    public Payment markPaymentSucceeded(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));
        payment.setStatus("SUCCEEDED");
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        Invoice invoice = payment.getInvoice();
        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);
        return payment;
    }
}