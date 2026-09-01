package com.college.tenantbilling.Service;

import com.college.tenantbilling.DTO.PlanRequest;
import com.college.tenantbilling.Model.Plan;
import com.college.tenantbilling.Repo.PlanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepo planRepository;
    public Plan createPlan(PlanRequest request) {
        Plan plan = new Plan(
                request.getName(),
                request.getBasePrice(),
                request.getBillingInterval(),
                request.getUsageLimit()
        );
        return planRepository.save(plan);
    }
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found: " + id));
    }
    public Plan updatePlan(Long id, PlanRequest request) {
        Plan plan = getPlanById(id);
        plan.setName(request.getName());
        plan.setBasePrice(request.getBasePrice());
        plan.setBillingInterval(request.getBillingInterval());
        plan.setUsageLimit(request.getUsageLimit());
        return planRepository.save(plan);
    }
    public void deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            throw new IllegalArgumentException("Plan not found: " + id);
        }
        planRepository.deleteById(id);
    }
}