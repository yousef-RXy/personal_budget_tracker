package com.se.income.utils;

import java.time.LocalDate;

import com.se.income.Repository.IncomeRepository;
import com.se.income.dto.EntryDTO;
import com.se.income.model.IncomeModel;
import com.se.income.model.RepetitionPeriod;

public class IncomeUtils {
    public static IncomeModel DTOtoModel(EntryDTO entryDTO, IncomeModel income) {
        income.setName(entryDTO.getName());
        income.setAmount(entryDTO.getAmount());
        income.setCategory(entryDTO.getCategory());

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