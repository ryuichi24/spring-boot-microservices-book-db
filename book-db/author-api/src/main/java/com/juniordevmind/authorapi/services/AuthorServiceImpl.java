package com.juniordevmind.authorapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Override
    public List<Author> getAuthors() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Author getAuthor(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Author createAuthor(CreateAuthorDto dto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteAuthor(long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateAuthor(UpdateAuthorDto dto) {
        // TODO Auto-generated method stub

    }

}
