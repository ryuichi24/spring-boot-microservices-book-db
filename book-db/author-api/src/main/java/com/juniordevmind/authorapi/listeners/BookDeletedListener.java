package com.juniordevmind.authorapi.listeners;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
public class BookDeletedListener {
    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;

    @RabbitListener(queues = RabbitMQKeys.AUTHOR_API_BOOK_DELETED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) { 
        log.info("{} got triggered. Message: {}", BookDeletedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        Optional<Book> result = _bookRepository.findById(bookEventDto.getId());
        if(result.isEmpty()) {
            return;
        }

        Book book = result.get();
        _bookRepository.delete(book);

        List<Author> authors = _authorRepository.findAllById(bookEventDto.getAuthors());

        for(Author authorItem: authors) {
            List<UUID> filtered = authorItem.getBooks().stream()
                .filter(bookId -> !bookId.equals(bookEventDto.getId())).toList();
            authorItem.setBooks(filtered);
            _authorRepository.save(authorItem);
        }

    }
}
