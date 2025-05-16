package com.venkat.expense.tracker.category;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String categoryName;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
