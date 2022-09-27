package com.juniordevmind.commentapi.config;

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
    // comment created
    @Bean
    public FanoutExchange commentCreatedExchange() {
        return new FanoutExchange(RabbitMQKeys.COMMENT_CREATED_EXCHANGE);
    }

    // comment updated
    @Bean
    public FanoutExchange commentUpdatedExchange() {
        return new FanoutExchange(RabbitMQKeys.COMMENT_UPDATED_EXCHANGE);
    }

    // comment deleted
    @Bean
    public FanoutExchange commentDeletedExchange() {
        return new FanoutExchange(RabbitMQKeys.COMMENT_DELETED_EXCHANGE);
    }

    // book created event
    @Bean
    public Queue bookCreatedQueue() {
        return new Queue(RabbitMQKeys.COMMENT_API_BOOK_CREATED_QUEUE);
    }

    @Bean
    public FanoutExchange bookCreatedExchange() {
        return new FanoutExchange(RabbitMQKeys.BOOK_CREATED_EXCHANGE);
    }

    @Bean
    public Binding bookCreatedBinding(Queue bookCreatedQueue, FanoutExchange bookCreatedExchange) {
        return BindingBuilder
                .bind(bookCreatedQueue)
                .to(bookCreatedExchange);
    }

    // book updated event
    @Bean
    public Queue bookUpdatedQueue() {
        return new Queue(RabbitMQKeys.COMMENT_API_BOOK_UPDATED_QUEUE);
    }

    @Bean
    public FanoutExchange bookUpdatedExchange() {
        return new FanoutExchange(RabbitMQKeys.BOOK_UPDATED_EXCHANGE);
    }

    @Bean
    public Binding bookUpdatedBinding(Queue bookUpdatedQueue, FanoutExchange bookUpdatedExchange) {
        return BindingBuilder
                .bind(bookUpdatedQueue)
                .to(bookUpdatedExchange);
    }

    // book deleted event
    @Bean
    public Queue bookDeletedQueue() {
        return new Queue(RabbitMQKeys.COMMENT_API_BOOK_DELETED_QUEUE);
    }

    @Bean
    public FanoutExchange bookDeletedExchange() {
        return new FanoutExchange(RabbitMQKeys.BOOK_DELETED_EXCHANGE);
    }

    @Bean
    public Binding bookDeletedBinding(Queue bookDeletedQueue, FanoutExchange bookDeletedExchange) {
        return BindingBuilder
                .bind(bookDeletedQueue)
                .to(bookDeletedExchange);
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