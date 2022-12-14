package com.juniordevmind.authorapi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juniordevmind.authorapi.dtos.AuthorDto;
import com.juniordevmind.authorapi.dtos.BookDto;
import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.mappers.AuthorMapper;
import com.juniordevmind.authorapi.mappers.BookMapper;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.models.Book;
import com.juniordevmind.authorapi.repositories.AuthorRepository;
import com.juniordevmind.authorapi.repositories.BookRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.AuthorEventDto;
import com.juniordevmind.shared.errors.NotFoundException;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional()
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository _authorRepository;
    private final BookRepository _bookRepository;
    private final AuthorMapper _authorMapper;
    private final BookMapper _bookMapper;
    private final RabbitTemplate _template;

    @Override
    public List<AuthorDto> getAuthors() {
        List<Author> authors =  _authorRepository.findAll();
        List<AuthorDto> authorDtos = authors.stream().map(authorItem -> _authorMapper.toDto(authorItem)).toList();
        return authorDtos;
    }

    @Override
    public AuthorDto getAuthor(UUID id) {
        Author author = _findAuthorById(id);
        List<BookDto> books = new ArrayList<>();
        for (UUID bookId : author.getBooks()) {
            Optional<Book> result = _bookRepository.findById(bookId);
            if (result.isPresent()) {
                books.add(_bookMapper.toDto(result.get()));
            }
        }
        AuthorDto authorDto = _authorMapper.toDto(author);
        authorDto.setBookList(books);
        return authorDto;
    }

    @Override
    public AuthorDto createAuthor(CreateAuthorDto dto) {
        Author newAuthor = _authorRepository.save(
                Author.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .books(dto.getBooks())
                        .build());

        CustomMessage<AuthorEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(LocalDateTime.now());
        msg.setPayload(_authorMapper.toEventDto(newAuthor));
        _template.convertAndSend(RabbitMQKeys.AUTHOR_CREATED_EXCHANGE, "", msg);

        return _authorMapper.toDto(newAuthor);
    }

    @Override
    public void deleteAuthor(UUID id) {
        Author author = _findAuthorById(id);
        _authorRepository.delete(author);
        CustomMessage<AuthorEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(LocalDateTime.now());
        msg.setPayload(_authorMapper.toEventDto(author));
        _template.convertAndSend(RabbitMQKeys.AUTHOR_DELETED_EXCHANGE, "", msg);
    }

    @Override
    public void updateAuthor(UpdateAuthorDto dto, UUID id) {
        Author found = _findAuthorById(id);

        if (Objects.nonNull(dto.getName())) {
            found.setName(dto.getName());
        }

        if (Objects.nonNull(dto.getDescription())) {
            found.setDescription(dto.getDescription());
        }

        if (Objects.nonNull(dto.getBooks())) {
            found.setBooks(dto.getBooks());
        }

        Author updated = _authorRepository.save(found);
        AuthorEventDto authorEventDto = _authorMapper.toEventDto(updated);
        CustomMessage<AuthorEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(LocalDateTime.now());
        msg.setPayload(authorEventDto);
        _template.convertAndSend(RabbitMQKeys.AUTHOR_UPDATED_EXCHANGE, "", msg);
    }

    private Author _findAuthorById(UUID id) {
        Optional<Author> result = _authorRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }

}
