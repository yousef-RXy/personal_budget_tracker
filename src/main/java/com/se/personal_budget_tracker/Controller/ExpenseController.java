package com.se.personal_budget_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.se.personal_budget_tracker.Service.ExpenseService;
import com.se.personal_budget_tracker.dto.EntryDTO;
import com.se.personal_budget_tracker.model.ExpenseModel;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @GetMapping
  public List<ExpenseModel> getAllExpenses(@RequestHeader("User-Id") Long userId) {
    return expenseService.getAllExpenses(userId);
  }

  @GetMapping("/{category}")
  public List<ExpenseModel> getCategoryExpenses(@PathVariable String category, @RequestHeader("User-Id") Long userId) {
    return expenseService.getCategoryExpenses(userId, category);
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
