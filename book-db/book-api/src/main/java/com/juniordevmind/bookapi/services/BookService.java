package com.juniordevmind.bookapi.services;

import java.util.List;

import com.juniordevmind.bookapi.dtos.CreateBookDto;
import com.juniordevmind.bookapi.dtos.UpdateBookDto;
import com.juniordevmind.bookapi.models.Book;

public interface BookService {
    public List<Book> getBooks();

    public Book getBook(long id);

    public Book createBook(CreateBookDto dto);

    public void deleteBook(long id);

    public void updateBook(UpdateBookDto dto, long id);
}
