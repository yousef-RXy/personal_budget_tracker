package com.se.personal_budget_tracker.utils;

import java.time.LocalDate;

import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.dto.EntryDTO;
import com.se.personal_budget_tracker.model.IncomeModel;
import com.se.personal_budget_tracker.model.RepetitionPeriod;

public class IncomeUtils {
    public static IncomeModel DTOtoModel(EntryDTO entryDTO, IncomeModel income) {
        income.setName(entryDTO.getName());
        income.setAmount(entryDTO.getAmount());
        income.setCategory(entryDTO.getCategory());
        income.setRepetitive(entryDTO.getRepetitionPeriod() != null);

        income.setDate(entryDTO.getDate() == null ? LocalDate.now() : entryDTO.getDate());
        income.setRepetitionPeriod(
                entryDTO.getRepetitionPeriod() == null ? RepetitionPeriod.None
                        : entryDTO.getRepetitionPeriod());

        return income;
    }

    public static IncomeModel getIncomeModelByID(IncomeRepository incomerepo, long incomeId) throws RuntimeException {
        IncomeModel incomeModel = incomerepo.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        return incomeModel;
    }

    public static boolean checkIncomeOwner(IncomeModel income, long userID) {
        return income.getUser().getId() == userID;

    }

}