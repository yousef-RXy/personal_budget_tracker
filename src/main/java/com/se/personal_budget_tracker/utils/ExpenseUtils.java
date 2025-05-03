package com.se.personal_budget_tracker.utils;

import java.time.LocalDate;
import java.util.Objects;

import com.se.personal_budget_tracker.Repository.ExpenseRepository;
import com.se.personal_budget_tracker.dto.EntryDTO;
import com.se.personal_budget_tracker.model.ExpenseModel;
import com.se.personal_budget_tracker.model.RepetitionPeriod;
import com.se.personal_budget_tracker.model.UserModel;

public class ExpenseUtils {
  public static ExpenseModel getExpenseByID(ExpenseRepository expenseRepository, Long expenseId)
      throws RuntimeException {
    ExpenseModel expense = expenseRepository.findById(expenseId)
        .orElseThrow(() -> new RuntimeException("expense not found"));
    return expense;
  }

  public static ExpenseModel DTOtoModel(EntryDTO entryDTO, ExpenseModel expense) {
    expense.setName(entryDTO.getName());
    expense.setAmount(entryDTO.getAmount());
    expense.setCategory(entryDTO.getCategory());

    expense.setDate(entryDTO.getDate() == null ? LocalDate.now() : entryDTO.getDate());
    expense.setRepetitionPeriod(
        entryDTO.getRepetitionPeriod() == null ? RepetitionPeriod.None
            : entryDTO.getRepetitionPeriod());

    return expense;
  }

  public static boolean checkExpenseOwner(ExpenseModel expense, long userID) {
    UserModel user = expense.getUser();

    return Objects.equals(user.getId(), userID);
  }
}
