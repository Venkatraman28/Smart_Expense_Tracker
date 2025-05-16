package com.venkat.expense.tracker.transaction;

import com.venkat.expense.tracker.entities.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public TransactionResponse toTransactionMapper(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .categoryId(transaction.getCategory().getId())
                .userId(transaction.getUser().getId())
                .amount(transaction.getAmount())
                .categoryName(transaction.getCategory().getName())
                .description(transaction.getDescription())
                .paymentMethod(transaction.getPaymentMethod())
                .type(transaction.getType())
                .build();
    }
}
