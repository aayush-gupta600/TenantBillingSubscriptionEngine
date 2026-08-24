package com.college.tenantbilling.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_records")
@Getter
@Setter
@NoArgsConstructor
public class UsageRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(name = "metric_type", nullable = false)
    private String metricType;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt = LocalDateTime.now();

    public UsageRecord(Subscription subscription, String metricType, BigDecimal quantity) {
        this.subscription = subscription;
        this.metricType = metricType;
        this.quantity = quantity;
    }
}