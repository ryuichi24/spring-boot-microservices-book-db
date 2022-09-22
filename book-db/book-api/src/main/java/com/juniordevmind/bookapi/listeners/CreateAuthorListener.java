package com.juniordevmind.bookapi.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.models.CustomMessage;

@Component
public class CreateAuthorListener {
    @RabbitListener(queues = RabbitMQKeys.BOOK_API_QUEUE)
    public void handleMessage(CustomMessage message) {
        System.out.println(message.toString());
    }
}
