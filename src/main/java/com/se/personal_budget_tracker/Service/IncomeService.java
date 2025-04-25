package com.se.personal_budget_tracker.Service;
import com.se.personal_budget_tracker.Repository.IncomeRepository;
import com.se.personal_budget_tracker.Repository.UserRepository;
import com.se.personal_budget_tracker.dto.IncomeDTO;
import com.se.personal_budget_tracker.model.IncomeModel;
import com.se.personal_budget_tracker.model.UserModel;
import com.se.personal_budget_tracker.utils.BalanceUtils;
import com.se.personal_budget_tracker.utils.IncomeUtils;
import com.se.personal_budget_tracker.utils.UserUtils;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {
private final UserRepository userRepository;
private final IncomeRepository incomeRepository;
    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }
public boolean addIncome(IncomeDTO incomeDTO) {
    try {
        long userID = incomeDTO.getUserId();
        UserModel user = UserUtils.getUserByID(userRepository,userID);
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
public boolean DeleteIncome(Long incomeID, Long userID){
    try{
        IncomeModel income = IncomeUtils.getIncomeModelByID(incomeRepository, incomeID);
        boolean isOwner = IncomeUtils.checkIncomeOwner(income, userID);
        if(!isOwner){
            return false;
        }
        long amount = income.getAmount();
        BalanceUtils.decreaseBalance(userRepository, userID, amount);
        incomeRepository.delete(income);
        return true;
    }
    catch (RuntimeException e){
        return false;
    }
}
public boolean UpdateIncome(IncomeDTO incomeDTO, Long IncomeID){
    try{
        IncomeModel income = IncomeUtils.getIncomeModelByID(incomeRepository, IncomeID);
        Long user = income.getUser().getId();
        boolean isOwner = IncomeUtils.checkIncomeOwner(income, user);
        if(!isOwner){
            return  false;
        }
        boolean isEnoughBalance =BalanceUtils.decreaseBalance(userRepository, user, income.getAmount());
        if (!isEnoughBalance) {
            return false;
        }
        BalanceUtils.increaseBalance(userRepository, user, incomeDTO.getAmount());
        IncomeUtils.DTOtoModel(incomeDTO, income);
        incomeRepository.save(income);
        return true;
    }
    catch (RuntimeException e){
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