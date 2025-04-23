package com.se.personal_budget_tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se.personal_budget_tracker.model.IncomeModel;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeModel, Long> {
}
