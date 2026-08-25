package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    List<Subscription> findByTenantId(Long tenantId);
}