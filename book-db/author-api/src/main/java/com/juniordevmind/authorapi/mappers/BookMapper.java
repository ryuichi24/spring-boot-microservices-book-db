package com.juniordevmind.authorapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.authorapi.dtos.BookDto;
import com.juniordevmind.authorapi.models.Book;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final ModelMapper _modelMapper;

    public BookDto toDto(Book book) {
        return _modelMapper.map(book, BookDto.class);
    }
}
