package com.se.expense.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.se.expense.Repository.ExpenseRepository;
import com.se.expense.Repository.UserRepository;
import com.se.expense.model.ExpenseModel;
import com.se.expense.model.RepetitionPeriod;
import com.se.expense.utils.BalanceUtils;

@Component
public class RepetitiveTransactionScheduler {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *") // 10 min for testing/ every 12 am -> (0 0 0 * * *)
    public void addRepetitiveExpenses() {
        List<ExpenseModel> repetitiveExpenses = expenseRepository.findByRepetitionPeriodNot(RepetitionPeriod.None);
        for (ExpenseModel expense : repetitiveExpenses) {
            LocalDate lastDate = expense.getDate();
            LocalDate today = LocalDate.now();
            boolean createRepetitive = false;
            switch (expense.getRepetitionPeriod()) {
                case Daily -> createRepetitive = lastDate.plusDays(1).isEqual(today);
                case Yearly -> createRepetitive = lastDate.plusYears(1).isEqual(today);
                case Weekly -> createRepetitive = lastDate.plusWeeks(1).isEqual(today);
                case Monthly -> createRepetitive = lastDate.plusMonths(1).isEqual(today);
                default -> {
                }
            }
            if (createRepetitive) {
                boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository, expense.getUser().getId(),
                        expense.getAmount());
                if (!isEnoughBalance) {
                    continue;
                }
                ExpenseModel newExpense = new ExpenseModel();
                newExpense.setName(expense.getName());
                newExpense.setUser(expense.getUser());
                newExpense.setAmount(expense.getAmount());
                newExpense.setCategory(expense.getCategory());
                newExpense.setDate(LocalDate.now());
                newExpense.setRepetitionPeriod(expense.getRepetitionPeriod());
                expenseRepository.save(newExpense);
            }
        }
    }
}