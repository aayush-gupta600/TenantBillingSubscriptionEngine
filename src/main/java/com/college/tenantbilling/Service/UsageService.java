package com.college.tenantbilling.Service;

import com.college.tenantbilling.DTO.UsageRecordRequest;
import com.college.tenantbilling.Model.Subscription;
import com.college.tenantbilling.Model.UsageRecord;
import com.college.tenantbilling.Repo.SubscriptionRepo;
import com.college.tenantbilling.Repo.UsageRecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageService {

    @Autowired
    private UsageRecordRepo usageRecordRepository;

    @Autowired
    private SubscriptionRepo subscriptionRepository;
    public UsageRecord logUsage(Long subscriptionId, UsageRecordRequest request) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));
        UsageRecord record = new UsageRecord(subscription, request.getMetricType(), request.getQuantity());
        return usageRecordRepository.save(record);
    }
    public List<UsageRecord> getUsageForSubscription(Long subscriptionId) {
        return usageRecordRepository.findBySubscriptionId(subscriptionId);
    }
}