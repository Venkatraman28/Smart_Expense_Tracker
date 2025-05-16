package com.venkat.expense.tracker.repository;

import com.venkat.expense.tracker.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select c.* from category c where c.user_id = ?1",  nativeQuery = true)
    List<Category> findCategoriesByUserId(String userId);

    @Query(value = "select c.* from category c where c.id = ?1", nativeQuery = true)
    Optional<Category> findCategoryById(Long id);
}
