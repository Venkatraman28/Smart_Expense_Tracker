package com.venkat.expense.tracker.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<CategoryResponse>> getAllCategories(Authentication authentication) {
        try {
            return ResponseEntity.ok(categoryService.getAllCategoriesByUser(authentication));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/create",  method = RequestMethod.POST)
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("categoryId") Long categoryId, Authentication authentication) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryById(categoryId, authentication));
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "/delete/{categoryId}",  method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId, Authentication authentication) {
        try {
            categoryService.deleteCategory(categoryId, authentication);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
