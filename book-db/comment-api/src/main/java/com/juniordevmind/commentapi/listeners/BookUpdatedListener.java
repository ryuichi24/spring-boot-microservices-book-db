package com.juniordevmind.commentapi.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juniordevmind.commentapi.mappers.BookMapper;
import com.juniordevmind.commentapi.models.Book;
import com.juniordevmind.commentapi.repositories.BookRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.BookEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookUpdatedListener {
    private final BookRepository _bookRepository;
    private final BookMapper _bookMapper;

    @RabbitListener(queues = RabbitMQKeys.COMMENT_API_BOOK_UPDATED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) {
        log.info("{} got triggered. Message: {}", BookUpdatedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        Book newBook = _bookMapper.toEntity(bookEventDto);
        _bookRepository.save(newBook);
    }
}