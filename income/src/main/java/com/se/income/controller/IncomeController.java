package com.se.income.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.se.income.Service.IncomeService;
import com.se.income.Service.IncomeSummaryService;
import com.se.income.dto.CategoryTotalDTO;
import com.se.income.dto.EntryDTO;
import com.se.income.dto.SummaryDTO;
import com.se.income.model.IncomeModel;

@RestController
public class IncomeController {
  private final IncomeService incomeService;
  private final IncomeSummaryService summaryService;

  public IncomeController(IncomeService incomeService, IncomeSummaryService summaryService) {
    this.incomeService = incomeService;
    this.summaryService = summaryService;
  }

  @GetMapping
  public List<IncomeModel> getAllIncome(@RequestHeader("User-Id") Long userId) {
    return incomeService.getAllIncome(userId);
  }

  @GetMapping("/category")
  public List<IncomeModel> getCategoryIncome(@RequestHeader("Category") String category,
      @RequestHeader("User-Id") Long userId) {
    return incomeService.getCategoryIncome(userId, category);
  }

  @GetMapping("/summary")
  public SummaryDTO getSummary(@RequestHeader("User-Id") Long userId) {
    return summaryService.getSummary(userId);
  }

  @GetMapping("/pie")
  public List<CategoryTotalDTO> getPie(@RequestHeader("User-Id") Long userId) {
    return summaryService.getPie(userId);
  }

  @PostMapping
  public boolean addIncome(@RequestBody EntryDTO incomeDTO) {
    return incomeService.addIncome(incomeDTO);
  }

  @PutMapping("/{incomeID}")
  public boolean updateIncome(@PathVariable Long incomeID, @RequestBody EntryDTO updatedIncome) {
    return incomeService.UpdateIncome(updatedIncome, incomeID);
  }

  @DeleteMapping("/{incomeID}")
  public boolean deleteIncome(@PathVariable Long incomeID, @RequestHeader("User-Id") Long userId) {
    return incomeService.DeleteIncome(incomeID, userId);
  }

}
