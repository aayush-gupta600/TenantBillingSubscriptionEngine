package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepo extends JpaRepository<Invoice, Long> {
    List<Invoice> findBySubscriptionId(Long subscriptionId);
    List<Invoice> findBySubscriptionTenantId(Long tenantId);
}