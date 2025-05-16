package com.venkat.expense.tracker.budget;

import com.venkat.expense.tracker.entities.Budget;
import org.springframework.stereotype.Service;

@Service
public class BudgetMapper {

    public BudgetResponse toBudgetMapper(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .categoryId(budget.getCategory().getId())
                .userId(budget.getUser().getId())
                .amount(budget.getAmount())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .categoryName(budget.getCategory().getName())
                .budgetPeriod(budget.getBudgetPeriod())
                .budgetType(budget.getBudgetType())
                .build();
    }
}
