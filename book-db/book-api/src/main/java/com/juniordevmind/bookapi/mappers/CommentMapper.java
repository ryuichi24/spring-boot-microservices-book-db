package com.juniordevmind.bookapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.bookapi.dtos.CommentDto;
import com.juniordevmind.bookapi.models.Comment;
import com.juniordevmind.shared.domain.CommentEventDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper _modelMapper;

    public Comment toEntity(CommentEventDto eventDto) {
        return _modelMapper.map(eventDto, Comment.class);
    }

    public CommentDto toDto(Comment entity) {
        return _modelMapper.map(entity, CommentDto.class);
    }
}
