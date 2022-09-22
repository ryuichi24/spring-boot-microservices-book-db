package com.juniordevmind.authorapi.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.repositories.AuthorRepository;

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
        Optional<Author> result = _authorRepository.findById(id);

        if (result.isEmpty()) {
            throw new Error("Not found");
        }

        return result.get();
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
        _authorRepository.deleteById(id);
    }

    @Override
    public void updateAuthor(UpdateAuthorDto dto, long id) {
        Optional<Author> result = _authorRepository.findById(id);

        if (result.isEmpty()) {
            throw new Error("Not found");
        }

        Author found = result.get();

        if (Objects.nonNull(dto.getName())) {
            found.setName(dto.getName());
        }

        if (Objects.nonNull(dto.getDescription())) {
            found.setDescription(dto.getDescription());
        }

        _authorRepository.save(found);
    }

}
