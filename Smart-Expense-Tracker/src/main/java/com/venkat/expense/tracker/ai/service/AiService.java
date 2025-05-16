package com.venkat.expense.tracker.ai.service;

import com.venkat.expense.tracker.ampq.PythonRPCSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class AiService {

    private final PythonRPCSender rpcSenderAndReceiver;

    public Map<String, String> getCategoryFromPrediction(String description) {
        PythonRequest pythonRequest = new PythonRequest();
        pythonRequest.setClassName("Category");
        pythonRequest.setMethodName("createCategory");
        pythonRequest.setData(Map.of("description", description));
        return rpcSenderAndReceiver.sendToPython("create.category", pythonRequest);
    }

}
