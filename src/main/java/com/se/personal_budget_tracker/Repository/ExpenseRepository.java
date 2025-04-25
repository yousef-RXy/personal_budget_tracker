package com.se.personal_budget_tracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.se.personal_budget_tracker.model.ExpenseModel;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, Long> {
  List<ExpenseModel> findByUserId(Long userId);
  List<ExpenseModel> findByIsRepetitiveTrue();
  List<ExpenseModel> findByUserIdAndCategory(Long userId, String category);
}
