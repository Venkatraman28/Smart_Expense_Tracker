package com.venkat.expense.tracker.budget;

import com.venkat.expense.tracker.entities.Budget;
import com.venkat.expense.tracker.entities.Category;
import com.venkat.expense.tracker.entities.User;
import com.venkat.expense.tracker.repository.BudgetRepository;
import com.venkat.expense.tracker.repository.CategoryRepository;
import com.venkat.expense.tracker.utils.CommonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final CommonServiceImpl commonServiceImpl;
    private final BudgetMapper budgetMapper;

    public List<BudgetResponse> getAllBudgets(Authentication  authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                List<Budget> budgets = budgetRepository.findBudgetsByUserId(user.getId());
                return budgets
                        .stream()
                        .map(budget -> budgetMapper.toBudgetMapper(budget))
                        .toList();
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("BudgetService getAllBudgets: ", e);
            throw new Exception("Failed to fetch all budgets: " + e.getMessage(), e);
        }
    }

    public BudgetResponse createBudget(BudgetRequest budgetRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(budgetRequest.getUserId());
            if (user != null) {
                Category category = categoryRepository.findById(budgetRequest.getCategoryId()).orElseThrow(
                        () -> new Exception("Unable to fetch category"));
                Budget budget = new Budget();
                budget.setCategory(category);
                budget.setUser(user);
                budget.setAmount(budgetRequest.getAmount());
                budget.setStartDate(budgetRequest.getStartDate());
                budget.setEndDate(budgetRequest.getEndDate());
                budget.setBudgetType(budgetRequest.getBudgetType());
                budget.setBudgetPeriod(budgetRequest.getBudgetPeriod());

                budgetRepository.saveAndFlush(budget);
                return budgetMapper.toBudgetMapper(budget);
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("BudgetService createBudget: ", e);
            throw new Exception("Failed to fetch budget: " + e.getMessage(), e);
        }
    }

    public BudgetResponse getBudget(Long budgetId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null)
                return budgetMapper.toBudgetMapper(budgetRepository.findById(budgetId).orElseThrow(
                        () -> new Exception("Unable to find budget")));
            else
                throw new Exception("User not found");
        }  catch (Exception e) {
            log.error("BudgetService getBudget: ", e);
            throw new Exception("Failed to fetch budget: " + e.getMessage(), e);
        }
    }

    public BudgetResponse updateBudget(BudgetRequest budgetRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(budgetRequest.getUserId());
            if (user != null) {
                Category category = categoryRepository.findById(budgetRequest.getCategoryId()).orElseThrow(
                        () -> new Exception("Unable to fetch category"));
                Budget budget = budgetRepository.findById(budgetRequest.getId()).orElseThrow(
                        () -> new Exception("Unable to find budget"));
                budget.setCategory(category);
                budget.setAmount(budgetRequest.getAmount());
                budget.setStartDate(budgetRequest.getStartDate());
                budget.setEndDate(budgetRequest.getEndDate());
                budget.setBudgetType(budgetRequest.getBudgetType());
                budget.setBudgetPeriod(budgetRequest.getBudgetPeriod());

                budgetRepository.saveAndFlush(budget);
                return budgetMapper.toBudgetMapper(budget);
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("BudgetService updateBudget: ", e);
            throw new Exception("Failed to fetch budget: " + e.getMessage(), e);
        }
    }

    public void deleteBudget(Long budgetId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                Budget budget = budgetRepository.findById(budgetId).orElseThrow(
                        () -> new Exception("Unable to find budget"));
                budgetRepository.delete(budget);
            }  else
                throw new Exception("User not found");
        }  catch (Exception e) {
            log.error("BudgetService deleteBudget: ", e);
            throw new Exception("Failed to fetch budget: " + e.getMessage(), e);
        }
    }
}
