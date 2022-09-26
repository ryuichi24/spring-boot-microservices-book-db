package com.juniordevmind.commentapi.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.juniordevmind.commentapi.dtos.CommentDto;
import com.juniordevmind.commentapi.models.Comment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper _modelMapper;

    public CommentDto toDto(Comment comment) {
        return _modelMapper.map(comment, CommentDto.class);
    }

}