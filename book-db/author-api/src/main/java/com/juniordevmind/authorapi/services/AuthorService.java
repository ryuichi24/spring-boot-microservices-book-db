package com.juniordevmind.authorapi.services;

import java.util.List;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;

public interface AuthorService {
    public List<Author> getAuthors();
    public Author getAuthor(long id);
    public Author createAuthor(CreateAuthorDto dto);
    public void deleteAuthor(long id);
    public void updateAuthor(UpdateAuthorDto dto);
}
