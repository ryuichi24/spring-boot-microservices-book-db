package com.juniordevmind.bookapi.listeners;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.juniordevmind.bookapi.mappers.AuthorMapper;
import com.juniordevmind.bookapi.models.Author;
import com.juniordevmind.bookapi.models.Book;
import com.juniordevmind.bookapi.repositories.AuthorRepository;
import com.juniordevmind.bookapi.repositories.BookRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.AuthorEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorUpdatedListener {
    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;
    private final AuthorMapper _authorMapper;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.BOOK_API_AUTHOR_UPDATED_QUEUE)
    public void handleMessage(CustomMessage<AuthorEventDto> message) {
        log.info("{} got triggered. Message: {}", AuthorUpdatedListener.class, message.toString());
        AuthorEventDto authorEventDto = message.getPayload();
        Author newAuthor = _authorMapper.toEntity(authorEventDto);
        _authorRepository.save(newAuthor);

        List<Book> books = _bookRepository.findAllById(authorEventDto.getBooks());

        for (Book bookItem : books) {
            if (!bookItem.getAuthors().contains(authorEventDto.getId())) {
                bookItem.getAuthors().add(authorEventDto.getId());
            }
        }
    }
}
