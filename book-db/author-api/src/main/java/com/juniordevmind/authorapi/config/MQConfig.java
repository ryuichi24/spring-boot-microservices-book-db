package com.juniordevmind.authorapi.config;

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

// https://stackoverflow.com/questions/32220312/rabbitmq-amqp-best-practice-queue-topic-design-in-a-microservice-architecture
@Configuration
public class MQConfig {
    // author created event
    @Bean
    public FanoutExchange authorCreatedExchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_CREATED_EXCHANGE);
    }

    // author updated event
    @Bean
    public FanoutExchange authorUpdatedExchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_UPDATED_EXCHANGE);
    }

    // author deleted event
    @Bean
    public FanoutExchange authorDeletedExchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_DELETED_EXCHANGE);
    }

    // book created event
    @Bean
    public Queue bookCreatedQueue() {
        return new Queue(RabbitMQKeys.AUTHOR_API_BOOK_CREATED_QUEUE);
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
        return new Queue(RabbitMQKeys.AUTHOR_API_BOOK_UPDATED_QUEUE);
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

    // others
    // https://stackoverflow.com/a/27952529/13723015
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
