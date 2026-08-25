package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.InvoiceLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceLineItemRepo extends JpaRepository<InvoiceLineItem, Long> {
}