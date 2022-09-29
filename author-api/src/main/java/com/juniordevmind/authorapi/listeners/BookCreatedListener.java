package com.juniordevmind.authorapi.listeners;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class BookCreatedListener {
    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;
    private final BookMapper _bookMapper;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.AUTHOR_API_BOOK_CREATED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) {
        log.info("{} got triggered. Message: {}", BookCreatedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        Optional<Book> result = _bookRepository.findById(bookEventDto.getId());
        if (result.isPresent()) {
            return;
        }

        _bookRepository.save(_bookMapper.toEntity(bookEventDto));

        if (Objects.isNull(bookEventDto.getAuthors()) || bookEventDto.getAuthors().size() == 0) {
            return;
        }

        List<Author> authors = _authorRepository.findAllById(bookEventDto.getAuthors());

        for (Author authorItem : authors) {
            if (!authorItem.getBooks().contains(bookEventDto.getId())) {
                authorItem.getBooks().add(bookEventDto.getId());
            }
        }

    }
}
