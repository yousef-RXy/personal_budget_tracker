package com.se.personal_budget_tracker.utils;

import java.time.LocalDate;

import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.dto.IncomeDTO;
import com.se.personal_budget_tracker.model.IncomeModel;

public class IncomeUtils {
public static IncomeModel DTOtoModel(IncomeDTO incomeDTO, IncomeModel income) {
    income.setName(incomeDTO.getName());
    income.setAmount(incomeDTO.getAmount());
    income.setCategory(incomeDTO.getCategory());
    income.setDate(incomeDTO.getDate() == null? LocalDate.now(): incomeDTO.getDate());
    income.setRepetitive(incomeDTO.isRepetitive());
    income.setRepitionPeriod(incomeDTO.getRepitionPeriod());
    return income;
}
public static IncomeModel getIncomeModelByID(IncomeRepository incomerepo, long incomeId ) throws RuntimeException {
    IncomeModel incomeModel = incomerepo.findById(incomeId).orElse(null);
    return incomeModel;}
    public static boolean checkIncomeOwner(IncomeModel income, long userID) {
        return income.getUser().getId() == userID;
    
}

}