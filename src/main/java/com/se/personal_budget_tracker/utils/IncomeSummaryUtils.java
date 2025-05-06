package com.se.personal_budget_tracker.utils;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.se.personal_budget_tracker.Repository.IncomeRepository;

@Component
public class IncomeSummaryUtils {

  private final IncomeRepository incomeRepository;

  public IncomeSummaryUtils(IncomeRepository incomeRepository) {
    this.incomeRepository = incomeRepository;
  }

  public Long getTotalThisWeek(Long userId) {
    LocalDate today = LocalDate.now();
    LocalDate start = getStartOfWeek(today);
    LocalDate end = today;
    return getTotalAmount(userId, start, end);
  }

  public Long getTotalThisMonth(Long userId) {
    LocalDate start = LocalDate.now().withDayOfMonth(1);
    LocalDate end = LocalDate.now();
    return getTotalAmount(userId, start, end);
  }

  public Long getTotalThisYear(Long userId) {
    LocalDate start = LocalDate.now().withDayOfYear(1);
    LocalDate end = LocalDate.now();
    return getTotalAmount(userId, start, end);
  }

  public Long getTotalLastWeek(Long userId) {
    LocalDate today = LocalDate.now();
    LocalDate end = getStartOfWeek(today).minusDays(1); // Friday of last week
    LocalDate start = end.minusDays(6); // Saturday of last week
    return getTotalAmount(userId, start, end);
  }

  public Long getTotalLastMonth(Long userId) {
    LocalDate start = LocalDate.now().minusMonths(1).withDayOfMonth(1);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
    return getTotalAmount(userId, start, end);
  }

  public Long getTotalLastYear(Long userId) {
    LocalDate start = LocalDate.now().minusYears(1).withDayOfYear(1);
    LocalDate end = start.withDayOfYear(start.lengthOfYear());
    return getTotalAmount(userId, start, end);
  }

  private Long getTotalAmount(Long userId, LocalDate start, LocalDate end) {
    return Optional.ofNullable(
        incomeRepository.getTotalIncomeByUserIdAndDateRange(userId, start, end)).orElse(0L);
  }

  private LocalDate getStartOfWeek(LocalDate date) {
    int daysFromSaturday = (date.getDayOfWeek().getValue() + 1) % 7;
    return date.minusDays(daysFromSaturday);
  }
}
