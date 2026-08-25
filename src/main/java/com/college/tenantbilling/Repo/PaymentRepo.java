package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByInvoiceId(Long invoiceId);
}