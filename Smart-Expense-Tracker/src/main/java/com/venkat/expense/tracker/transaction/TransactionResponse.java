package com.venkat.expense.tracker.transaction;

import com.venkat.expense.tracker.entities.Transaction;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private Long categoryId;
    private String userId;
    private String description;
    private BigDecimal amount;
    private String categoryName;
    private Transaction.PaymentMethod paymentMethod;
    private Transaction.Type type;
}
