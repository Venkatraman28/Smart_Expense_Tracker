package com.venkat.expense.tracker.budget;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/budget")
public class BudgetController {

    private final BudgetService budgetService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<BudgetResponse>> getAllBudgets(Authentication authentication) {
        try {
            return ResponseEntity.ok(budgetService.getAllBudgets(authentication));
        }   catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @RequestMapping(value = "/create", method =  RequestMethod.POST)
    public ResponseEntity<BudgetResponse> createBudget(@RequestBody BudgetRequest budgetRequest) {
        try {
            return ResponseEntity.ok(budgetService.createBudget(budgetRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/{budgetId}", method = RequestMethod.GET)
    public ResponseEntity<BudgetResponse> getBudget(@PathVariable("budgetId") Long budgetId, Authentication authentication) {
        try {
            return ResponseEntity.ok(budgetService.getBudget(budgetId, authentication));
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update",  method =  RequestMethod.POST)
    public ResponseEntity<BudgetResponse> updateBudget(@RequestBody BudgetRequest budgetRequest) {
        try {
            return ResponseEntity.ok(budgetService.updateBudget(budgetRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete",  method =  RequestMethod.DELETE)
    public ResponseEntity<String> deleteBudget(@PathVariable("budgetId") Long budgetId, Authentication authentication) {
        try {
            budgetService.deleteBudget(budgetId, authentication);
            return ResponseEntity.ok("Budget Deleted");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
