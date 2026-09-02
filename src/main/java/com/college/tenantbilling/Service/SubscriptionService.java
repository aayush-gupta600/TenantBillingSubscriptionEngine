package com.college.tenantbilling.Service;

import com.college.tenantbilling.DTO.SubscriptionRequest;
import com.college.tenantbilling.Model.Plan;
import com.college.tenantbilling.Model.Subscription;
import com.college.tenantbilling.Model.Tenant;
import com.college.tenantbilling.Repo.PlanRepo;
import com.college.tenantbilling.Repo.SubscriptionRepo;
import com.college.tenantbilling.Repo.TenantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepo subscriptionRepository;

    @Autowired
    private TenantRepo tenantRepository;

    @Autowired
    private PlanRepo planRepository;
    public Subscription subscribe(Long tenantId, SubscriptionRequest request) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found: " + tenantId));

        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + request.getPlanId()));

        Subscription subscription = new Subscription(tenant, plan);
        return subscriptionRepository.save(subscription);
    }
    public Subscription changePlan(Long subscriptionId, SubscriptionRequest request) {
        Subscription subscription = getSubscriptionById(subscriptionId);

        Plan newPlan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + request.getPlanId()));

        subscription.setPlan(newPlan);
        return subscriptionRepository.save(subscription);
    }

    public Subscription cancelSubscription(Long subscriptionId) {
        Subscription subscription = getSubscriptionById(subscriptionId);
        subscription.setStatus("CANCELLED");
        return subscriptionRepository.save(subscription);
    }

    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + id));
    }

    public List<Subscription> getSubscriptionsByTenant(Long tenantId) {
        return subscriptionRepository.findByTenantId(tenantId);
    }
}