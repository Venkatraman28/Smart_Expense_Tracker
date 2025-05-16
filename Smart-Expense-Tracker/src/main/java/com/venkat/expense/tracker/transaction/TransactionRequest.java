package com.venkat.expense.tracker.transaction;

import com.venkat.expense.tracker.entities.Transaction;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long id;
    private BigDecimal amount;
    private String description;
    private String userId;
    private Long categoryId;
    private Transaction.PaymentMethod paymentMethod;
    private Transaction.Type type;
}
