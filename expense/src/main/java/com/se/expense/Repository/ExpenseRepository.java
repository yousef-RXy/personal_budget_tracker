package com.se.expense.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se.expense.model.ExpenseModel;
import com.se.expense.model.RepetitionPeriod;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseModel, Long> {
  List<ExpenseModel> findByUserId(Long userId);

  List<ExpenseModel> findByRepetitionPeriodNot(RepetitionPeriod none);

  List<ExpenseModel> findByUserIdAndCategory(Long userId, String category);

  @Query("SELECT SUM(e.amount) FROM ExpenseModel e WHERE e.user.id = :userId AND e.date BETWEEN :startDate AND :endDate")
  Long getTotalAmountByUserIdAndDateRange(@Param("userId") Long userId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query("SELECT e.category, SUM(e.amount) " +
      "FROM ExpenseModel e " +
      "WHERE e.user.id = :userId " +
      "GROUP BY e.category")
  List<Object[]> getTotalAmountByUserGroupedByCategory(@Param("userId") Long userId);
}
