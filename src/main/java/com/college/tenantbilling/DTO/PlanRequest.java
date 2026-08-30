package com.college.tenantbilling.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PlanRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal basePrice;

    @NotBlank
    private String billingInterval; // MONTHLY / YEARLY

    @NotNull
    @Positive
    private Integer usageLimit;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getBillingInterval() { return billingInterval; }
    public void setBillingInterval(String billingInterval) { this.billingInterval = billingInterval; }

    public Integer getUsageLimit() { return usageLimit; }
    public void setUsageLimit(Integer usageLimit) { this.usageLimit = usageLimit; }
}