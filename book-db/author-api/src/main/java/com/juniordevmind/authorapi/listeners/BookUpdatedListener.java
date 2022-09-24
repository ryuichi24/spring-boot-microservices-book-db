package com.juniordevmind.authorapi.listeners;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juniordevmind.authorapi.mappers.BookMapper;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.models.Book;
import com.juniordevmind.authorapi.repositories.AuthorRepository;
import com.juniordevmind.authorapi.repositories.BookRepository;
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
    private final AuthorRepository _authorRepository;
    private final BookMapper _bookMapper;

    @RabbitListener(queues = RabbitMQKeys.AUTHOR_API_BOOK_UPDATED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) {
        log.info("{} got triggered. Message: {}", BookUpdatedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        Book newBook = _bookMapper.toEntity(bookEventDto);
        _bookRepository.save(newBook);

        List<Author> authors = _authorRepository.findAllById(bookEventDto.getAuthors());

        for (Author authorItem : authors) {
            if (!authorItem.getBooks().contains(bookEventDto.getId())) {
                authorItem.getBooks().add(bookEventDto.getId());
                _authorRepository.save(authorItem);
            }
        }
    }
}
