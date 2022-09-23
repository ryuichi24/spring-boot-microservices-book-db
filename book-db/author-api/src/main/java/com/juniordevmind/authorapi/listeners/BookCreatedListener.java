package com.juniordevmind.authorapi.listeners;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juniordevmind.authorapi.models.Book;
import com.juniordevmind.authorapi.repositories.BookRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.BookEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookCreatedListener {
    private final BookRepository _bookRepository;

    @RabbitListener(queues = RabbitMQKeys.AUTHOR_API_BOOK_CREATED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) {
        log.info("{} got triggered. Message: {}", BookCreatedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        Optional<Book> result = _bookRepository.findById(bookEventDto.getId());
        if (result.isPresent()) {
            return;
        }
        Book newBook = new Book();
        newBook.setId(bookEventDto.getId());
        newBook.setTitle(bookEventDto.getTitle());
        newBook.setDescription(bookEventDto.getDescription());
        newBook.setCreatedAt(bookEventDto.getCreatedAt());
        newBook.setUpdatedAt(bookEventDto.getUpdatedAt());
        _bookRepository.save(newBook);
    }
}
