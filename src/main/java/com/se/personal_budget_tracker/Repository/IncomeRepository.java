package com.se.personal_budget_tracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.se.personal_budget_tracker.model.IncomeModel;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeModel, Long> {
    List<IncomeModel> findByUserId(Long userId);

    List<IncomeModel> findByUserIdAndCategory(Long userId, String category);

    List<IncomeModel> findByIsRepetitiveTrue();

}
