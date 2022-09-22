package com.juniordevmind.bookapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juniordevmind.shared.constants.RabbitMQKeys;

// https://www.youtube.com/watch?v=YLsG0mew2dU
@Configuration
public class MQConfig {
    @Bean
    public Queue bookApiQueue() {
        return new Queue(RabbitMQKeys.BOOK_API_QUEUE);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(RabbitMQKeys.AUTHOR_EXCHANGE);
    }

    @Bean
    public Binding bookApiBinding(Queue bookApiQueue, FanoutExchange exchange) {
        return BindingBuilder
                .bind(bookApiQueue)
                .to(exchange);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}