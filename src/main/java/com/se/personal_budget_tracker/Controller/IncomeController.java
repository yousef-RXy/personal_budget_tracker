package com.se.personal_budget_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.se.personal_budget_tracker.Service.IncomeService;
import com.se.personal_budget_tracker.dto.IncomeDTO;
import com.se.personal_budget_tracker.model.IncomeModel;

@RestController
@RequestMapping("/Income")
public class IncomeController {
private final IncomeService incomeService;
public IncomeController(IncomeService incomeService) {
    this.incomeService = incomeService;     }
@PostMapping
public boolean addIncome(@RequestBody IncomeDTO incomeDTO) {
    return incomeService.addIncome(incomeDTO);}
@PutMapping("/{incomeID}")
public boolean updateIncome(@PathVariable Long incomeID, @RequestBody IncomeDTO updatedIncome) {
    return incomeService.UpdateIncome(updatedIncome, incomeID);
}
@DeleteMapping("/{incomeID}")
public boolean deleteIncome(@PathVariable Long incomeID, @RequestHeader("User-Id") Long userId) {
    return incomeService.DeleteIncome(incomeID, userId);
}
@GetMapping
public List<IncomeModel> getAllIncome(@RequestHeader("User-Id") Long userId) {
    return incomeService.getAllIncome(userId);
}

@GetMapping("/{category}")
public List<IncomeModel> getCategoryIncome(@PathVariable String category, @RequestHeader("User-Id") Long userId) {
    return incomeService.getCategoryIncome(userId, category);
}

}
