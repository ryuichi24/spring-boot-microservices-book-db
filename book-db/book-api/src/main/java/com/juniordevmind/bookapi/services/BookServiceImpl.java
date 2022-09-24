package com.juniordevmind.bookapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
import com.juniordevmind.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository _bookRepository;
    private final AuthorRepository _authorRepository;
    private final AuthorMapper _authorMapper;
    private final BookMapper _bookMapper;

    @Override
    public List<Book> getBooks() {
        // return _bookRepository.findAll();
        return _bookRepository.findAll();
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
        bookDto.setAuthors(authorDtos);
        return bookDto;
    }

    @Override
    public Book createBook(CreateBookDto dto) {
        return _bookRepository.save(
                Book.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .authors(dto.getAuthors())
                        .build());
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

        _bookRepository.save(found);
    }

    private Book _findBookById(UUID id) {
        Optional<Book> result = _bookRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }

}
