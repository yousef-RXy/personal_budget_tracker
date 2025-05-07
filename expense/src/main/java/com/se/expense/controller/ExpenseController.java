package com.se.expense.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.se.expense.Service.ExpenseService;
import com.se.expense.Service.ExpenseSummaryService;
import com.se.expense.dto.CategoryTotalDTO;
import com.se.expense.dto.EntryDTO;
import com.se.expense.dto.SummaryDTO;
import com.se.expense.model.ExpenseModel;

@RestController
public class ExpenseController {
  private final ExpenseService expenseService;
  private final ExpenseSummaryService summaryService;

  public ExpenseController(ExpenseService expenseService, ExpenseSummaryService summaryService) {
    this.expenseService = expenseService;
    this.summaryService = summaryService;
  }

  @GetMapping
  public List<ExpenseModel> getAllExpenses(@RequestHeader("User-Id") Long userId) {
    return expenseService.getAllExpenses(userId);
  }

  @GetMapping("/category")
  public List<ExpenseModel> getCategoryExpenses(@RequestHeader("Category") String category,
      @RequestHeader("User-Id") Long userId) {
    return expenseService.getCategoryExpenses(userId, category);
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
  public boolean addExpense(@RequestBody EntryDTO expense) {
    return expenseService.addExpense(expense);
  }

  @DeleteMapping("/{expenseID}")
  public boolean deleteExpense(@PathVariable Long expenseID, @RequestHeader("User-Id") Long userId) {
    return expenseService.deleteExpense(expenseID, userId);
  }

  @PutMapping("/{expenseID}")
  public boolean updateExpense(@PathVariable Long expenseID, @RequestBody EntryDTO updatedExpense) {
    return expenseService.updateExpense(expenseID, updatedExpense);
  }

}
