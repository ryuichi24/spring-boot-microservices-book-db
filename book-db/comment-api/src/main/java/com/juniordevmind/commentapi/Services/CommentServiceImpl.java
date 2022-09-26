package com.juniordevmind.commentapi.Services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.juniordevmind.commentapi.dtos.BookDto;
import com.juniordevmind.commentapi.dtos.CommentDto;
import com.juniordevmind.commentapi.dtos.CreateCommentDto;
import com.juniordevmind.commentapi.dtos.UpdateCommentDto;
import com.juniordevmind.commentapi.mappers.BookMapper;
import com.juniordevmind.commentapi.mappers.CommentMapper;
import com.juniordevmind.commentapi.models.Book;
import com.juniordevmind.commentapi.models.Comment;
import com.juniordevmind.commentapi.repositories.BookRepository;
import com.juniordevmind.commentapi.repositories.CommentRepository;
import com.juniordevmind.shared.errors.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository _commentRepository;
    private final BookRepository _bookRepository;
    private final CommentMapper _commentMapper;
    private final BookMapper _bookMapper;

    @Override
    public List<Comment> getComments(UUID bookId) {
        return _commentRepository.findAllByBookId(bookId);
    }

    @Override
    public CommentDto getComment(UUID id) {
        Comment comment = _findCommentById(id);
        Optional<Book> book = _bookRepository.findById(comment.getBookId());
        if (book.isEmpty()) {
            throw new NotFoundException("A Book of the comment is not found.");
        }
        CommentDto commentDto = _commentMapper.toDto(comment);
        BookDto bookDto = _bookMapper.toDto(book.get());
        commentDto.setBook(bookDto);
        return commentDto;
    }

    @Override
    public Comment createComment(CreateCommentDto dto) {
        return _commentRepository.save(
                Comment.builder()
                        .content(dto.getContent())
                        .bookId(dto.getBookId())
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
