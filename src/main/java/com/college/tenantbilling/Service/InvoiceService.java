package com.college.tenantbilling.Service;

import com.college.tenantbilling.Model.*;
import com.college.tenantbilling.Repo.InvoiceRepo;
import com.college.tenantbilling.Repo.SubscriptionRepo;
import com.college.tenantbilling.Repo.UsageRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceService {
    private static final BigDecimal OVERAGE_RATE_PER_UNIT = new BigDecimal("0.50");
    @Autowired
    private InvoiceRepo invoiceRepository;
    @Autowired
    private SubscriptionRepo subscriptionRepository;
    @Autowired
    private UsageRecordRepo usageRecordRepository;
    public Invoice generateInvoice(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));

        Plan plan = subscription.getPlan();
        List<UsageRecord> usageRecords = usageRecordRepository.findBySubscriptionId(subscriptionId);
        BigDecimal totalUsage = usageRecords.stream()
                .map(UsageRecord::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Invoice invoice = new Invoice(subscription);
        InvoiceLineItem baseFee = new InvoiceLineItem(
                invoice,
                plan.getName() + " plan - base fee",
                plan.getBasePrice(),
                1
        );
        invoice.getLineItems().add(baseFee);

        BigDecimal total = plan.getBasePrice();
        BigDecimal limit = BigDecimal.valueOf(plan.getUsageLimit());
        if (totalUsage.compareTo(limit) > 0) {
            BigDecimal overageUnits = totalUsage.subtract(limit);
            BigDecimal overageAmount = overageUnits.multiply(OVERAGE_RATE_PER_UNIT);

            InvoiceLineItem overageFee = new InvoiceLineItem(
                    invoice,
                    "Usage overage (" + overageUnits + " units)",
                    overageAmount,
                    1
            );
            invoice.getLineItems().add(overageFee);
            total = total.add(overageAmount);
        }
        invoice.setTotalAmount(total);
        invoice.setStatus("ISSUED");
        return invoiceRepository.save(invoice);
    }
    public List<Invoice> getInvoicesForSubscription(Long subscriptionId) {
        return invoiceRepository.findBySubscriptionId(subscriptionId);
    }
    public List<Invoice> getInvoicesForTenant(Long tenantId) {
        return invoiceRepository.findBySubscriptionTenantId(tenantId);
    }
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
    }
}