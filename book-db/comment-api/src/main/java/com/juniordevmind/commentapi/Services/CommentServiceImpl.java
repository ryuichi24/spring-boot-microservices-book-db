package com.juniordevmind.commentapi.Services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.juniordevmind.commentapi.dtos.CreateCommentDto;
import com.juniordevmind.commentapi.dtos.UpdateCommentDto;
import com.juniordevmind.commentapi.models.Comment;
import com.juniordevmind.commentapi.repositories.CommentRepository;
import com.juniordevmind.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository _commentRepository;

    @Override
    public List<Comment> getComments() {
        return _commentRepository.findAll();
    }

    @Override
    public Comment getComment(UUID id) {
        return _findCommentById(id);
    }

    @Override
    public Comment createComment(CreateCommentDto dto) {
        return _commentRepository.save(
                Comment.builder()
                        .content(dto.getContent())
                        .build());
    }

    @Override
    public void deleteComment(UUID id) {
        Comment comment = _findCommentById(id);
        _commentRepository.delete(comment);

    }

    @Override
    public void updateComment(UpdateCommentDto dto, UUID id) {
        Comment found = _findCommentById(id);

        if (Objects.nonNull(dto.getContent())) {
            found.setContent(dto.getContent());
        }

        _commentRepository.save(found);

    }

    private Comment _findCommentById(UUID id) {
        Optional<Comment> result = _commentRepository.findById(id);

        if (result.isEmpty()) {
            throw new NotFoundException("Not found with this ID: " + id);
        }

        return result.get();
    }
}
