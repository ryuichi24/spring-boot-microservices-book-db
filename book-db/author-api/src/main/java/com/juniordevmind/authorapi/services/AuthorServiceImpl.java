package com.juniordevmind.authorapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.juniordevmind.authorapi.dtos.AuthorDto;
import com.juniordevmind.authorapi.dtos.BookDto;
import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.models.Book;
import com.juniordevmind.authorapi.repositories.AuthorRepository;
import com.juniordevmind.authorapi.repositories.BookRepository;
import com.juniordevmind.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository _authorRepository;
    private final BookRepository _bookRepository;

    @Override
    public List<Author> getAuthors() {
        return _authorRepository.findAll();
    }

    @Override
    public AuthorDto getAuthor(UUID id) {
        Author author = _findAuthorById(id);
        List<BookDto> books = new ArrayList<>();
        for (UUID bookId : author.getBooks()) {
            Optional<Book> result = _bookRepository.findById(bookId);
            if (result.isPresent()) {
                Book book = result.get();
                BookDto bookDto = new BookDto();
                bookDto.setId(id);
                bookDto.setTitle(book.getTitle());
                bookDto.setDescription(book.getDescription());
                bookDto.setCreatedAt(book.getCreatedAt());
                bookDto.setUpdatedAt(book.getUpdatedAt());
                books.add(bookDto);
            }
        }
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setDescription(author.getDescription());
        authorDto.setBooks(books);
        authorDto.setCreatedAt(author.getCreatedAt());
        authorDto.setUpdatedAt(author.getUpdatedAt());
        return authorDto;
    }

    @Override
    public Author createAuthor(CreateAuthorDto dto) {
        return _authorRepository.save(
                Author.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .build());
    }

    @Override
    public void deleteAuthor(UUID id) {
        Author author = _findAuthorById(id);
        _authorRepository.delete(author);
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

        _authorRepository.save(found);
    }

    private Author _findAuthorById(UUID id) {
        Optional<Author> result = _authorRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }

}
