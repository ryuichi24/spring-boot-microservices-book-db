package com.juniordevmind.bookapi.listeners;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
public class AuthorDeletedListener {
    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;

    @RabbitListener(queues = RabbitMQKeys.BOOK_API_AUTHOR_DELETED_QUEUE)
    public void handleMessage(CustomMessage<AuthorEventDto> message) {
        log.info("{} got triggered. Message: {}", AuthorCreatedListener.class, message.toString());
        AuthorEventDto authorEventDto = message.getPayload();
        Optional<Author> result = _authorRepository.findById(authorEventDto.getId());
        if (result.isEmpty()) {
            return;
        }

        Author author = result.get();
        _authorRepository.delete(author);

        List<Book> books = _bookRepository.findAllById(authorEventDto.getBooks());

        for (Book bookItem : books) {
            List<UUID> filtered = bookItem.getAuthors().stream()
                    .filter(authorId -> !authorId.equals(authorEventDto.getId())).toList();
            bookItem.setAuthors(filtered);
            _bookRepository.save(bookItem);
        }

    }
}
