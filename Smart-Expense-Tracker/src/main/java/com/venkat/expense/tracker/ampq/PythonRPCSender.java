package com.venkat.expense.tracker.ampq;

import com.venkat.expense.tracker.ai.service.PythonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PythonRPCSender {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public Map<String, String> sendToPython(String routingKey, PythonRequest request) {

        Object response = rabbitTemplate.convertSendAndReceive(directExchange.getName(), routingKey, request);

        if (response instanceof Map) {
            Map<?, ?> rawMap = (Map<?, ?>) response;
            return rawMap.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> String.valueOf(e.getKey()),
                            e -> String.valueOf(e.getValue())
                    ));
        } else {
            throw new IllegalArgumentException("Unexpected response type: " + response);
        }
    }
}
