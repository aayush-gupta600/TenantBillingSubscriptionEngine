package com.college.tenantbilling.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UsageRecordRequest {

    @NotBlank
    private String metricType;   // e.g. "API_CALL", "STORAGE_GB"

    @NotNull
    @Positive
    private BigDecimal quantity;

    public String getMetricType() { return metricType; }
    public void setMetricType(String metricType) { this.metricType = metricType; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
}