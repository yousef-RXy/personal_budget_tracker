package com.se.personal_budget_tracker.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.se.personal_budget_tracker.Repository.ExpenseRepository;
import com.se.personal_budget_tracker.Repository.UserRepository;
import com.se.personal_budget_tracker.dto.ExpenseRequestDTO;
import com.se.personal_budget_tracker.model.ExpenseModel;
import com.se.personal_budget_tracker.model.UserModel;
import com.se.personal_budget_tracker.utils.BalanceUtils;
import com.se.personal_budget_tracker.utils.ExpenseUtils;
import com.se.personal_budget_tracker.utils.UserUtils;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public List<ExpenseModel> getAllExpenses(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public List<ExpenseModel> getCategoryExpenses(Long userId, String category) {
        return expenseRepository.findByUserIdAndCategory(userId, category);
    }

    public boolean addExpense(ExpenseRequestDTO expenseDTO) {
        try {
            ExpenseModel expense = new ExpenseModel();

            long userID = expenseDTO.getUserId();
            UserModel user = UserUtils.getUserByID(userRepository, userID);

            boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository, userID, expenseDTO.getAmount());
            if (!isEnoughBalance) {
                return false;
            }

            expense.setUser(user);
            ExpenseUtils.DTOtoModel(expenseDTO, expense);
            expenseRepository.save(expense);

            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean deleteExpense(Long expenseID, long userID) {
        try {
            ExpenseModel expense = ExpenseUtils.getExpenseByID(expenseRepository, expenseID);

            boolean isOwner = ExpenseUtils.checkExpenseOwner(expense, userID);
            if (!isOwner) {
                return false;
            }

            BalanceUtils.increaseBalance(userRepository, userID, expense.getAmount());

            if (!isOwner) {
                return false;
            }
            expenseRepository.delete(expense);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean updateExpense(Long expenseID, ExpenseRequestDTO expenseDTO) {
        try {
            ExpenseModel expense = ExpenseUtils.getExpenseByID(expenseRepository, expenseID);

            long userID = expenseDTO.getUserId();

            boolean isOwner = ExpenseUtils.checkExpenseOwner(expense, userID);
            if (!isOwner) {
                return false;
            }

            boolean isEnoughBalance = BalanceUtils.decreaseBalance(userRepository, userID, expenseDTO.getAmount(),
                    expense.getAmount());
            if (!isEnoughBalance) {
                return false;
            }

            ExpenseUtils.DTOtoModel(expenseDTO, expense);
            expenseRepository.save(expense);

            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
