package com.juniordevmind.commentapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.commentapi.dtos.BookDto;
import com.juniordevmind.commentapi.models.Book;
import com.juniordevmind.shared.domain.BookEventDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final ModelMapper _modelMapper;

    public BookDto toDto(Book book) {
        return _modelMapper.map(book, BookDto.class);
    }

    public BookEventDto toEventDto(Book book) {
        return _modelMapper.map(book, BookEventDto.class);
    }

    public Book toEntity(BookEventDto eventDto) {
        return _modelMapper.map(eventDto, Book.class);
    }
}
