package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageRecordRepo extends JpaRepository<UsageRecord, Long> {
    List<UsageRecord> findBySubscriptionId(Long subscriptionId);
    List<UsageRecord> findBySubscriptionIdAndRecordedAtBetween(Long subscriptionId, LocalDateTime start, LocalDateTime end);
}