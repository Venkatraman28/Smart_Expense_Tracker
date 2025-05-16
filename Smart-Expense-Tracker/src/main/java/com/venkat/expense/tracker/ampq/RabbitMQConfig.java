package com.venkat.expense.tracker.ampq;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final Environment environment;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue createCategoryQueue() {
        return new Queue(Objects.requireNonNull(environment.getProperty("create.category.queue")), true);
    }

    @Bean
    public Binding bindCreateCategory(Queue createCategoryQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createCategoryQueue).to(directExchange).with("create.category");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
