package com.juniordevmind.authorapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.authorapi.dtos.AuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.shared.domain.AuthorEventDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorMapper {
    private final ModelMapper _modelMapper;

    public AuthorDto toDto(Author author) {
        return _modelMapper.map(author, AuthorDto.class);
    }

    public AuthorEventDto toEventDto(Author author) {
        return _modelMapper.map(author, AuthorEventDto.class);
    }
}
