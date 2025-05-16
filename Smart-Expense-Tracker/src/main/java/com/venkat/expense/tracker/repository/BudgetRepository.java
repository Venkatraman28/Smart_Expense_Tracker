package com.venkat.expense.tracker.repository;

import com.venkat.expense.tracker.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query(value = "select b.* from budget b where user_id = ?1", nativeQuery = true)
    List<Budget> findBudgetsByUserId(String userId);
}
