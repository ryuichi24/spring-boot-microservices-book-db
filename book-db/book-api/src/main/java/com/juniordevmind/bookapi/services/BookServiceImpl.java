package com.juniordevmind.bookapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.juniordevmind.bookapi.dtos.AuthorDto;
import com.juniordevmind.bookapi.dtos.BookDto;
import com.juniordevmind.bookapi.dtos.CreateBookDto;
import com.juniordevmind.bookapi.dtos.UpdateBookDto;
import com.juniordevmind.bookapi.mappers.AuthorMapper;
import com.juniordevmind.bookapi.mappers.BookMapper;
import com.juniordevmind.bookapi.models.Author;
import com.juniordevmind.bookapi.models.Book;
import com.juniordevmind.bookapi.repositories.AuthorRepository;
import com.juniordevmind.bookapi.repositories.BookRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.BookEventDto;
import com.juniordevmind.shared.errors.NotFoundException;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;
    private final AuthorMapper _authorMapper;
    private final BookMapper _bookMapper;
    private final RabbitTemplate _template;

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = _bookRepository.findAll();
        List<BookDto> bookDtos = books.stream().map(bookItem -> _bookMapper.toDto(bookItem)).toList();
        return bookDtos;
    }

    @Override
    public BookDto getBook(UUID id) {
        Book book = _findBookById(id);
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (UUID authorId : book.getAuthors()) {
            Optional<Author> result = _authorRepository.findById(authorId);
            if (result.isPresent()) {
                authorDtos.add(_authorMapper.toDto(result.get()));
            }
        }
        BookDto bookDto = _bookMapper.toDto(book);
        bookDto.setAuthorList(authorDtos);
        return bookDto;
    }

    @Override
    public BookDto createBook(CreateBookDto dto) {
        Book newBook = _bookRepository.save(
                Book.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .authors(dto.getAuthors())
                        .build());

        CustomMessage<BookEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(LocalDateTime.now());
        BookEventDto bookEventDto = _bookMapper.toEventDto(newBook);
        msg.setPayload(bookEventDto);
        _template.convertAndSend(RabbitMQKeys.BOOK_CREATED_EXCHANGE, "", msg);

        return _bookMapper.toDto(newBook);
    }

    @Override
    public void deleteBook(UUID id) {
        Book book = _findBookById(id);
        _bookRepository.delete(book);

    }

    @Override
    public void updateBook(UpdateBookDto dto, UUID id) {
        Book found = _findBookById(id);

        if (Objects.nonNull(dto.getTitle())) {
            found.setTitle(dto.getTitle());
        }

        if (Objects.nonNull(dto.getDescription())) {
            found.setDescription(dto.getDescription());
        }

        if (Objects.nonNull(dto.getAuthors())) {
            found.setAuthors(dto.getAuthors());
        }

        Book updated = _bookRepository.save(found);
        BookEventDto bookEventDto = _bookMapper.toEventDto(updated);
        CustomMessage<BookEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(LocalDateTime.now());
        msg.setPayload(bookEventDto);
        _template.convertAndSend(RabbitMQKeys.BOOK_UPDATED_EXCHANGE, "", msg);

    }

    private Book _findBookById(UUID id) {
        Optional<Book> result = _bookRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }

}
