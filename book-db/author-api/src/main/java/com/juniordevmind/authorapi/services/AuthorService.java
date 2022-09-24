package com.juniordevmind.authorapi.services;

import java.util.List;
import java.util.UUID;

import com.juniordevmind.authorapi.dtos.AuthorDto;
import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;

public interface AuthorService {
    public List<Author> getAuthors();

    public AuthorDto getAuthor(UUID id);

    public Author createAuthor(CreateAuthorDto dto);

    public void deleteAuthor(UUID id);

    public void updateAuthor(UpdateAuthorDto dto, UUID id);
}
