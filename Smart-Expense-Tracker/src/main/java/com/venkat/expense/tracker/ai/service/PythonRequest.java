package com.venkat.expense.tracker.ai.service;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PythonRequest {

    private String className;
    private String methodName;
    private Map<String, Object> data;
}
