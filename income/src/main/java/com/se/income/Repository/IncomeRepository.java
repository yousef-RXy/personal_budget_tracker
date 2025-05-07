package com.se.income.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se.income.model.IncomeModel;
import com.se.income.model.RepetitionPeriod;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeModel, Long> {
        List<IncomeModel> findByUserId(Long userId);

        List<IncomeModel> findByRepetitionPeriodNot(RepetitionPeriod none);

        List<IncomeModel> findByUserIdAndCategory(Long userId, String category);

        @Query("SELECT SUM(i.amount) FROM IncomeModel i " +
                        "WHERE i.user.id = :userId AND i.date BETWEEN :startDate AND :endDate")
        Long getTotalIncomeByUserIdAndDateRange(
                        @Param("userId") Long userId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        @Query("SELECT i.category, SUM(i.amount) " +
                        "FROM IncomeModel i " +
                        "WHERE i.user.id = :userId " +
                        "GROUP BY i.category")
        List<Object[]> getTotalIncomeGroupedByCategory(@Param("userId") Long userId);
}
