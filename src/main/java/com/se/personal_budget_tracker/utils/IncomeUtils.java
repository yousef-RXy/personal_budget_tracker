package com.se.personal_budget_tracker.utils;

import java.time.LocalDate;

import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.dto.EntryDTO;
import com.se.personal_budget_tracker.model.IncomeModel;

public class IncomeUtils {
public static IncomeModel DTOtoModel(EntryDTO incomeDTO, IncomeModel income) {
    income.setName(incomeDTO.getName());
    income.setAmount(incomeDTO.getAmount());
    income.setCategory(incomeDTO.getCategory());
    income.setDate(incomeDTO.getDate() == null? LocalDate.now(): incomeDTO.getDate());
    income.setRepetitive(incomeDTO.isRepetitive());
    income.setRepitionPeriod(incomeDTO.getRepitionPeriod());
    return income;
}
public static IncomeModel getIncomeModelByID(IncomeRepository incomerepo, long incomeId ) throws RuntimeException {
    IncomeModel incomeModel = incomerepo.findById(incomeId).orElseThrow(() -> new RuntimeException("Income not found"));
    return incomeModel;}
    public static boolean checkIncomeOwner(IncomeModel income, long userID) {
        return income.getUser().getId() == userID;
    
}

}