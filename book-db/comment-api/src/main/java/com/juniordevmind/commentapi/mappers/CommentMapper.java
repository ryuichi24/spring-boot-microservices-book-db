package com.juniordevmind.commentapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.commentapi.dtos.CommentDto;
import com.juniordevmind.commentapi.models.Comment;
import com.juniordevmind.shared.domain.CommentEventDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper _modelMapper;

    public CommentDto toDto(Comment comment) {
        return _modelMapper.map(comment, CommentDto.class);
    }

    public CommentEventDto toEventDto(Comment entity) {
        return _modelMapper.map(entity, CommentEventDto.class);
    }
}