package com.juniordevmind.commentapi.Services;

import java.util.List;
import java.util.UUID;

import com.juniordevmind.commentapi.dtos.CommentDto;
import com.juniordevmind.commentapi.dtos.CreateCommentDto;
import com.juniordevmind.commentapi.dtos.UpdateCommentDto;
import com.juniordevmind.commentapi.models.Comment;

public interface CommentService {
    public List<Comment> getComments(UUID bookId);

    public CommentDto getComment(UUID id);

    public CommentDto createComment(CreateCommentDto dto);

    public void deleteComment(UUID id);

    public void updateComment(UpdateCommentDto dto, UUID id);
}
