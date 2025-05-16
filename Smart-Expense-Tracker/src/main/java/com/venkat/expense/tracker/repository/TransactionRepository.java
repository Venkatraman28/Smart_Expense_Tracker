package com.venkat.expense.tracker.repository;

import com.venkat.expense.tracker.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t.* from transactions t where t.user_id = ?1", nativeQuery = true)
    List<Transaction> findTransactionsByUserId(String userId);

    List<Transaction> findTransactionsByCategory_Id(Long categoryId);
}
