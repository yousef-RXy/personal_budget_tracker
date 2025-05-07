package com.se.income.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.se.income.Repository.IncomeRepository;
import com.se.income.Repository.UserRepository;
import com.se.income.model.IncomeModel;
import com.se.income.model.RepetitionPeriod;
import com.se.income.utils.BalanceUtils;

@Component
public class RepetitiveTransactionScheduler {
    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * *") // every 12 am / 10 min -> (0 */10 * * * *)
    public void addRepetitiveIncomes() {
        List<IncomeModel> repetitiveIncomes = incomeRepository.findByRepetitionPeriodNot(RepetitionPeriod.None);
        for (IncomeModel income : repetitiveIncomes) {
            LocalDate lastDate = income.getDate();
            LocalDate today = LocalDate.now();
            boolean createRepetitive = false;
            switch (income.getRepetitionPeriod()) {
                case Daily -> createRepetitive = lastDate.plusDays(1).isEqual(today);
                case Yearly -> createRepetitive = lastDate.plusYears(1).isEqual(today);
                case Weekly -> createRepetitive = lastDate.plusWeeks(1).isEqual(today);
                case Monthly -> createRepetitive = lastDate.plusMonths(1).isEqual(today);
                default -> {
                }
            }
            if (createRepetitive) {
                IncomeModel newIncome = new IncomeModel();
                newIncome.setName(income.getName());
                newIncome.setUser(income.getUser());
                newIncome.setCategory(income.getCategory());
                newIncome.setAmount(income.getAmount());
                newIncome.setDate(today);
                newIncome.setRepetitionPeriod(income.getRepetitionPeriod());
                incomeRepository.save(newIncome);
                BalanceUtils.increaseBalance(userRepository, income.getUser().getId(), income.getAmount());
            }
        }
    }

}