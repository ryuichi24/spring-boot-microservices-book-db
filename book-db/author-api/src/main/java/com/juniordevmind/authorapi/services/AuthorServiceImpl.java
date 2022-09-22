package com.juniordevmind.authorapi.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.repositories.AuthorRepository;
import com.juniordevmind.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository _authorRepository;

    @Override
    public List<Author> getAuthors() {
        return _authorRepository.findAll();
    }

    @Override
    public Author getAuthor(long id) {
        return _findAuthorById(id);
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
    public void deleteAuthor(long id) {
        Author author = _findAuthorById(id);
        _authorRepository.delete(author);
    }

    @Override
    public void updateAuthor(UpdateAuthorDto dto, long id) {
        Author found = _findAuthorById(id);

        if (Objects.nonNull(dto.getName())) {
            found.setName(dto.getName());
        }

        if (Objects.nonNull(dto.getDescription())) {
            found.setDescription(dto.getDescription());
        }

        _authorRepository.save(found);
    }

    private Author _findAuthorById(long id) {
        Optional<Author> result = _authorRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }

}
