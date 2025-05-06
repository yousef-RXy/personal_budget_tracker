package com.se.personal_budget_tracker.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.dto.CategoryTotalDTO;
import com.se.personal_budget_tracker.dto.SummaryDTO;
import com.se.personal_budget_tracker.utils.IncomeSummaryUtils;

@Service
public class IncomeSummaryService {
  private final IncomeSummaryUtils summaryUtils;
  private final IncomeRepository incomeRepository;

  public IncomeSummaryService(IncomeSummaryUtils summaryUtils, IncomeRepository incomeRepository) {
    this.summaryUtils = summaryUtils;
    this.incomeRepository = incomeRepository;
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
    return incomeRepository.getTotalIncomeGroupedByCategory(userId)
        .stream()
        .map(row -> new CategoryTotalDTO((String) row[0], (Long) row[1]))
        .collect(Collectors.toList());
  }

}
