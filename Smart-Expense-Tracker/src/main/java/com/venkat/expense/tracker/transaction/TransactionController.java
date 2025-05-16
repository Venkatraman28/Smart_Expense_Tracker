package com.venkat.expense.tracker.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(Authentication authentication) {
        try {
            return ResponseEntity.ok(transactionService.getAllTransactions(authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            return ResponseEntity.ok(transactionService.createTransaction(transactionRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<TransactionResponse> getTransactionDetails(@PathVariable("transactionId") Long transactionId, Authentication authentication) {
        try {
            return ResponseEntity.ok(transactionService.getTransaction(transactionId, authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            return ResponseEntity.ok(transactionService.updateTransaction(transactionRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{transactionId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTransaction(@PathVariable("transactionId") Long transactionId, Authentication authentication) {
        try {
            transactionService.deleteTransaction(transactionId, authentication);
            return ResponseEntity.ok("Transaction has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/by/category/{categoryId}",  method = RequestMethod.GET)
    public ResponseEntity<List<TransactionResponse>> transactionsByCategoryId(@PathVariable("categoryId") Long categoryId, Authentication authentication) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionsByCategoryId(categoryId, authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/summary",  method = RequestMethod.GET)
    public ResponseEntity<Map<String, BigDecimal>> transactionSummary(Authentication authentication) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionSummary(authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
