package com.venkat.expense.tracker.category;

import com.venkat.expense.tracker.entities.Category;
import com.venkat.expense.tracker.entities.User;
import com.venkat.expense.tracker.repository.CategoryRepository;
import com.venkat.expense.tracker.utils.CommonServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CommonServiceImpl commonServiceImpl;

    // GET Category - List all users categories
    public List<CategoryResponse> getAllCategoriesByUser(Authentication authentication) throws Exception {
        try {
            String userId = commonServiceImpl.getUserId(authentication);
            if (commonServiceImpl.checkIfUserValid(userId) != null) {
                List<Category> categories = categoryRepository.findCategoriesByUserId(userId);
                return categories
                        .stream()
                        .map(category -> categoryMapper.toCategoryResponseMapper(category))
                        .toList();
            } else
                throw new Exception("Unable to validate user");
        } catch (Exception e) {
            log.error("CategoryService.getAllCategoriesByUser(): ", e);
            throw new Exception("Failed to get categories: " + e.getMessage(), e);
        }
    }

    // Create category
    public CategoryResponse createCategory(CategoryRequest categoryRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(categoryRequest.getUserId());
            if (user != null) {
                Category category = new Category();
                category.setName(categoryRequest.getCategoryName());
                category.setUser(user);
                categoryRepository.saveAndFlush(category);
                return categoryMapper.toCategoryResponseMapper(category);
            } else
                throw new Exception("Unable to validate user");
        } catch (Exception e) {
            log.error("CategoryService.createCategory(): ", e);
            throw new Exception("Failed to create category: " + e.getMessage(), e);
        }
    }

    // Get category details
    public CategoryResponse getCategoryById(Long categoryId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null)
                return categoryMapper.toCategoryResponseMapper(categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new Exception("Unable to fetch category")));
            else
                throw new Exception("Unable to validate user");
        } catch (Exception e) {
            log.error("CategoryService.getCategoryById(): ", e);
            throw new Exception("Failed to get category details: " + e.getMessage(), e);
        }
    }

    // Update category details
    public CategoryResponse updateCategory(CategoryRequest categoryRequest) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(categoryRequest.getUserId());
            if (user != null) {
                Category category = categoryRepository.findById(categoryRequest.getId())
                        .orElseThrow(() -> new Exception("Category not found with ID: " + categoryRequest.getId()));

                category.setName(categoryRequest.getCategoryName());

                categoryRepository.saveAndFlush(category);
                return categoryMapper.toCategoryResponseMapper(category);
            } else
                throw new Exception("Unable to validate user");
        }  catch (Exception e) {
            log.error("CategoryService.updateCategory(): ", e);
            throw new Exception("Failed to update category: " + e.getMessage(), e);
        }
    }

    // Delete category
    public void deleteCategory(Long categoryId, Authentication authentication) throws Exception {
        try {
            User user = commonServiceImpl.checkIfUserValid(commonServiceImpl.getUserId(authentication));
            if (user != null) {
                Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new Exception("Category not found with ID: " + categoryId));
                categoryRepository.delete(category);
            } else
                throw new Exception("Unable to validate user");
        } catch (Exception e) {
            log.error("CategoryService.deleteCategory(): ", e);
            throw new Exception("Failed to delete category: " + e.getMessage(), e);
        }
    }



}
