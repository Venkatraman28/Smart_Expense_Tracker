package com.venkat.expense.tracker.category;

import com.venkat.expense.tracker.entities.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryResponse toCategoryResponseMapper(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .userId(category.getUser().getId())
                .firstName(category.getUser().getFirstName())
                .lastName(category.getUser().getLastName())
                .email(category.getUser().getEmail())
                .build();
    }
}
