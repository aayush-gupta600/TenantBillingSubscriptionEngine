package com.college.tenantbilling.DTO;

import jakarta.validation.constraints.NotNull;

public class SubscriptionRequest {

    @NotNull
    private Long planId;

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
}