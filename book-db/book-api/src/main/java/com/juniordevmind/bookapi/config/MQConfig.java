package com.juniordevmind.bookapi.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juniordevmind.shared.constants.RabbitMQKeys;

// https://www.youtube.com/watch?v=YLsG0mew2dU
@Configuration
public class MQConfig {
    // book created event
    @Bean
    public FanoutExchange bookCreatedExchange() {
        return new FanoutExchange(RabbitMQKeys.BOOK_CREATED_EXCHANGE);
    }

    // book updated event
    @Bean
    public FanoutExchange bookUpdatedExchange() {
        return new FanoutExchange(RabbitMQKeys.BOOK_UPDATED_EXCHANGE);
    }

    // author created event
    @Bean
    public Queue authorCreatedQueue() {
        return new Queue(RabbitMQKeys.BOOK_API_AUTHOR_CREATED_QUEUE);
    }

    @Bean
    public FanoutExchange authorCreatedExchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_CREATED_EXCHANGE);
    }

    @Bean
    public Binding authorCreatedBinding(Queue authorCreatedQueue, FanoutExchange authorCreatedExchange) {
        return BindingBuilder
                .bind(authorCreatedQueue)
                .to(authorCreatedExchange);
    }

    // author updated event
    @Bean
    public Queue authorUpdatedQueue() {
        return new Queue(RabbitMQKeys.BOOK_API_AUTHOR_UPDATED_QUEUE);
    }

    @Bean
    public FanoutExchange authorUpdatedExchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_UPDATED_EXCHANGE);
    }

    @Bean
    public Binding authorUpdatedBinding(Queue authorUpdatedQueue, FanoutExchange authorUpdatedExchange) {
        return BindingBuilder
                .bind(authorUpdatedQueue)
                .to(authorUpdatedExchange);
    }

    // others
    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}