package com.se.expense.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.se.expense.Repository.ExpenseRepository;
import com.se.expense.dto.CategoryTotalDTO;
import com.se.expense.dto.SummaryDTO;
import com.se.expense.utils.ExpenseSummaryUtils;

@Service
public class ExpenseSummaryService {
  private final ExpenseSummaryUtils summaryUtils;
  private final ExpenseRepository expenseRepository;

  public ExpenseSummaryService(ExpenseSummaryUtils summaryUtils, ExpenseRepository expenseRepository) {
    this.summaryUtils = summaryUtils;
    this.expenseRepository = expenseRepository;
  }

  public SummaryDTO getSummary(Long userId) {
    long thisWeek = summaryUtils.getTotalThisWeek(userId);
    long thisMonth = summaryUtils.getTotalThisMonth(userId);
    long thisYear = summaryUtils.getTotalThisYear(userId);

    long lastWeek = summaryUtils.getTotalLastWeek(userId);
    long lastMonth = summaryUtils.getTotalLastMonth(userId);
    long lastYear = summaryUtils.getTotalLastYear(userId);

    SummaryDTO summaryDTO = new SummaryDTO(thisWeek, thisMonth, thisYear, lastWeek, lastMonth,
        lastYear);

    return summaryDTO;
  }

  public List<CategoryTotalDTO> getPie(Long userId) {
    return expenseRepository.getTotalAmountByUserGroupedByCategory(userId)
        .stream()
        .map(row -> new CategoryTotalDTO((String) row[0], (Long) row[1]))
        .collect(Collectors.toList());
  }

}
