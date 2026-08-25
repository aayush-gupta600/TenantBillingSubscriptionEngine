package com.college.tenantbilling.Repo;

import com.college.tenantbilling.Model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Long> {
}