package com.se.income.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.se.income.Repository.IncomeRepository;
import com.se.income.Repository.UserRepository;
import com.se.income.dto.EntryDTO;
import com.se.income.model.IncomeModel;
import com.se.income.model.UserModel;
import com.se.income.utils.BalanceUtils;
import com.se.income.utils.IncomeUtils;
import com.se.income.utils.UserUtils;

@Service
public class IncomeService {
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    public boolean addIncome(EntryDTO incomeDTO) {
        try {
            long userID = incomeDTO.getUserId();
            UserModel user = UserUtils.getUserByID(userRepository, userID);
            long amount = incomeDTO.getAmount();
            BalanceUtils.increaseBalance(userRepository, userID, amount);
            IncomeModel income = new IncomeModel();
            income.setUser(user);
            IncomeUtils.DTOtoModel(incomeDTO, income);
            incomeRepository.save(income);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean DeleteIncome(Long incomeID, Long userID) {
        try {
            IncomeModel income = IncomeUtils.getIncomeModelByID(incomeRepository, incomeID);
            boolean isOwner = IncomeUtils.checkIncomeOwner(income, userID);
            if (!isOwner) {
                return false;
            }
            long amount = income.getAmount();

            boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository, userID, amount);
            if (!isEnoughBalance) {
                return false;
            }

            incomeRepository.delete(income);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean UpdateIncome(EntryDTO incomeDTO, Long IncomeID) {
        try {
            IncomeModel income = IncomeUtils.getIncomeModelByID(incomeRepository, IncomeID);
            Long user = income.getUser().getId();
            boolean isOwner = IncomeUtils.checkIncomeOwner(income, user);
            if (!isOwner) {
                return false;
            }
            if (incomeDTO.getAmount() >= income.getAmount()) {
                BalanceUtils.increaseBalance(userRepository, user, incomeDTO.getAmount() - income.getAmount());
            } else {
                boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository, user,
                        income.getAmount() - incomeDTO.getAmount());
                if (!isEnoughBalance) {
                    return false;
                }
            }
            IncomeUtils.DTOtoModel(incomeDTO, income);
            incomeRepository.save(income);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<IncomeModel> getAllIncome(Long userId) {
        return incomeRepository.findByUserId(userId);
    }

    public List<IncomeModel> getCategoryIncome(Long userId, String category) {
        return incomeRepository.findByUserIdAndCategory(userId, category);
    }
}