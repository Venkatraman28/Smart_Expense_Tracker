package com.venkat.expense.tracker.transaction;

import com.venkat.expense.tracker.ai.service.AiService;
import com.venkat.expense.tracker.category.CategoryRequest;
import com.venkat.expense.tracker.category.CategoryResponse;
import com.venkat.expense.tracker.category.CategoryService;
import com.venkat.expense.tracker.entities.Category;
import com.venkat.expense.tracker.entities.Transaction;
import com.venkat.expense.tracker.entities.User;
import com.venkat.expense.tracker.repository.CategoryRepository;
import com.venkat.expense.tracker.repository.TransactionRepository;
import com.venkat.expense.tracker.utils.CommonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CommonServiceImpl commonServiceImpl;
    private final TransactionMapper transactionMapper;
    private final AiService aiService;
    private final CategoryService categoryService;

    public List<TransactionResponse> getAllTransactions(Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                List<Transaction> transactions = transactionRepository.findTransactionsByUserId(user.getId());
                return transactions
                        .stream()
                        .map(transaction -> transactionMapper.toTransactionMapper(transaction))
                        .toList();
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("TransactionService.getAllTransactions(): ", e);
            throw new Exception("Failed to get transactions: " + e.getMessage(), e);
        }
    }

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(transactionRequest.getUserId());
            if (user != null) {
                Optional<Category> isCategory = categoryRepository.findCategoryById(transactionRequest.getCategoryId());
                Category category = null;
                if (isCategory.isPresent())
                    category = isCategory.get();
                else {
                    Map<String, String> response = aiService.getCategoryFromPrediction(transactionRequest.getDescription());
                    if (response.containsKey("status") && response.get("status").equals("error"))
                        throw new Exception("Unable to create category from transaction description: " + transactionRequest.getDescription());

                    CategoryRequest categoryRequest = new CategoryRequest();
                    categoryRequest.setCategoryName(response.get("categoryName"));
                    categoryRequest.setUserId(user.getId());
                    CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
                    if (categoryResponse.getId() == null)
                        throw new Exception("Unable to create category from transaction description: " + transactionRequest.getDescription());
                }

                Transaction transaction = new Transaction();
                transaction.setUser(user);
                transaction.setAmount(transactionRequest.getAmount());
                transaction.setCategory(category);
                transaction.setDescription(transactionRequest.getDescription());
                transaction.setType(transactionRequest.getType());
                transaction.setPaymentMethod(transactionRequest.getPaymentMethod());

                transactionRepository.saveAndFlush(transaction);
                return transactionMapper.toTransactionMapper(transaction);
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("TransactionService.createTransaction(): ", e);
            throw new Exception("Failed to create transaction: " + e.getMessage(), e);
        }
    }

    public TransactionResponse getTransaction(Long transactionId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null)
                return transactionMapper.toTransactionMapper(transactionRepository.findById(transactionId).orElseThrow(
                        () -> new Exception("Transaction not found with ID: " + transactionId)));
            else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("TransactionService.getTransaction(): ", e);
            throw new Exception("Failed to get transaction: " + e.getMessage(), e);
        }
    }

    public TransactionResponse updateTransaction(TransactionRequest transactionRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(transactionRequest.getUserId());
            if (user != null) {
                Category category = categoryRepository.findById(transactionRequest.getCategoryId()).orElseThrow(
                        () -> new Exception("Category not found with ID: " + transactionRequest.getCategoryId()));
                Transaction transaction = transactionRepository.findById(transactionRequest.getId()).orElseThrow(
                        () -> new Exception("Transaction not found with ID: " + transactionRequest.getId()));
                transaction.setAmount(transactionRequest.getAmount());
                transaction.setCategory(category);
                transaction.setDescription(transactionRequest.getDescription());
                transaction.setType(transactionRequest.getType());
                transaction.setPaymentMethod(transactionRequest.getPaymentMethod());
                transactionRepository.saveAndFlush(transaction);
                return transactionMapper.toTransactionMapper(transaction);
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("TransactionService.getTransaction(): ", e);
            throw new  Exception("Failed to get transaction: " + e.getMessage(), e);
        }
    }

    public void deleteTransaction(Long transactionId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                        () -> new Exception("Transaction not found with ID: " + transactionId));
                transactionRepository.delete(transaction);
            } else
                throw new Exception("User not found");
        } catch (Exception e) {
            log.error("TransactionService.getTransaction(): ", e);
            throw new Exception("Failed to delete transaction: " + e.getMessage(), e);
        }
    }

    public List<TransactionResponse> getTransactionsByCategoryId(Long categoryId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new  Exception("Category not found with ID: " + categoryId));
                List<Transaction> transactions = transactionRepository.findTransactionsByCategory_Id(categoryId);
                return transactions
                        .stream()
                        .map(transaction -> transactionMapper.toTransactionMapper(transaction))
                        .toList();
            } else
                throw new Exception("User not found");
        }  catch (Exception e) {
            log.error("TransactionService.getTransactionsByCategoryId(): ", e);
            throw new Exception("Failed to get transactions: " + e.getMessage(), e);
        }
    }

    public Map<String, BigDecimal> getTransactionSummary(Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            Map<String, BigDecimal> results = new HashMap<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            if (user != null) {
                List<Transaction> transactions = transactionRepository.findTransactionsByUserId(user.getId());
                for (Transaction transaction : transactions) {
                    String category = transaction.getCategory().getName();
                    BigDecimal amount =  transaction.getAmount();

                    results.put(category, results.getOrDefault(category, BigDecimal.ZERO).add(amount));
                    totalAmount.add(amount);
                }
                results.put("summary",  totalAmount);
                return results;
            }  else
                throw new Exception("User not found");
        }  catch (Exception e) {
            log.error("TransactionService.getTransactionSummary(): ", e);
            throw new Exception("Failed to get transactions: " + e.getMessage(), e);
        }
    }
}
