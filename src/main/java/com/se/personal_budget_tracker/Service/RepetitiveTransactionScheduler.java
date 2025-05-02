package com.se.personal_budget_tracker.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.se.personal_budget_tracker.Repository.ExpenseRepository;
import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.Repository.UserRepository;
import com.se.personal_budget_tracker.model.ExpenseModel;
import com.se.personal_budget_tracker.model.IncomeModel;
import com.se.personal_budget_tracker.model.RepetitionPeriod;
import com.se.personal_budget_tracker.utils.BalanceUtils;
import org.springframework.stereotype.Component;
@Component
public class RepetitiveTransactionScheduler {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Scheduled(cron = "0 0 0 * * *") //  every 12 am / 10 min  -> (0 */10 * * * *)
    public void addRepetitiveIncomes() {
        List<IncomeModel> repetitiveIncomes = incomeRepository.findByRepetitionPeriodNot(RepetitionPeriod.None);
        for (IncomeModel income : repetitiveIncomes) {
            LocalDate lastDate = income.getDate();
            LocalDate today = LocalDate.now();
            boolean createRepitive=false;
            switch (income.getRepetitionPeriod()) {
                case Daily:
                    createRepitive=lastDate.plusDays(1).isEqual(today);
                    break;
                case Yearly:
                    createRepitive=lastDate.plusYears(1).isEqual(today);
                    break;
                case Weekly:
                    createRepitive=lastDate.plusWeeks(1).isEqual(today);
                    break;
                case Monthly:
                    createRepitive=lastDate.plusMonths(1).isEqual(today);
                    break;
                default:
                    break;
            }
            if(createRepitive){
                IncomeModel newIncome = new IncomeModel();
                newIncome.setName(income.getName());
                newIncome.setUser(income.getUser());
                newIncome.setCategory(income.getCategory());
                newIncome.setAmount(income.getAmount());
                newIncome.setDate(today);
                newIncome.setRepetitionPeriod(income.getRepetitionPeriod());
                incomeRepository.save(newIncome);
                BalanceUtils.increaseBalance(userRepository,income.getUser().getId(), income.getAmount());
            }
        }
    }
    @Scheduled(cron = "0 0 0 * * *") // 10 min for testing/ every 12 am -> (0 0 0 * * *)
    public void addRepetitiveExpenses() {
        List<ExpenseModel> repetitiveExpenses= expenseRepository.findByRepetitionPeriodNot(RepetitionPeriod.None);
        for (ExpenseModel expense : repetitiveExpenses) {
            LocalDate lastDate = expense.getDate();
            LocalDate today = LocalDate.now();
            boolean createRepitive=false;
            switch (expense.getRepetitionPeriod()) {
                case Daily:
                    createRepitive=lastDate.plusDays(1).isEqual(today);
                    break;
                case Yearly:
                    createRepitive=lastDate.plusYears(1).isEqual(today);
                    break;
                case Weekly:
                    createRepitive=lastDate.plusWeeks(1).isEqual(today);
                    break;
                case Monthly:
                    createRepitive=lastDate.plusMonths(1).isEqual(today);
                    break;
                default:
                    break;
            }
            if(createRepitive){
                boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository,expense.getUser().getId(), expense.getAmount());
                if (!isEnoughBalance) {
                    expense.setDate(today);
                    expenseRepository.save(expense);
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