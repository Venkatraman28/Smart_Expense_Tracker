package com.venkat.expense.tracker.budget;

import com.venkat.expense.tracker.entities.Budget;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetResponse {
    private Long id;
    private Long categoryId;
    private String userId;
    private String categoryName;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Budget.BudgetType budgetType;
    private Budget.BudgetPeriod budgetPeriod;
}
