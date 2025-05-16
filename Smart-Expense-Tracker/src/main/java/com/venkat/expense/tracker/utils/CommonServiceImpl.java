package com.venkat.expense.tracker.utils;

import com.venkat.expense.tracker.entities.User;
import com.venkat.expense.tracker.repository.BudgetRepository;
import com.venkat.expense.tracker.repository.CategoryRepository;
import com.venkat.expense.tracker.repository.TransactionRepository;
import com.venkat.expense.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommonServiceImpl {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public User checkIfUserValid(String userId) throws Exception {
        try {
            if (userId != null) {
                return userRepository.findById(userId)
                        .orElseThrow(() -> new Exception("Unable to find user"));
            } else
                throw new Exception("User id is null");
        } catch (Exception e) {
            log.error("CommonUtils.checkIfUserValid: ", e);
        }
        return null;
    }

    public String getUserId(Authentication authentication) throws Exception {
        if (authentication.getName() != null)
            return authentication.getName();
        throw new Exception("Unable to retrieve user id from auth");
    }
}
