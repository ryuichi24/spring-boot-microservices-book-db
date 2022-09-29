package com.juniordevmind.bookapi.listeners;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.juniordevmind.bookapi.mappers.CommentMapper;
import com.juniordevmind.bookapi.models.Book;
import com.juniordevmind.bookapi.models.Comment;
import com.juniordevmind.bookapi.repositories.BookRepository;
import com.juniordevmind.bookapi.repositories.CommentRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.CommentEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentCreatedListener {
    private final BookRepository _bookRepository;
    private final CommentRepository _commentRepository;
    private final CommentMapper _commentMapper;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.BOOK_API_COMMENT_CREATED_QUEUE)
    public void handleMessage(CustomMessage<CommentEventDto> message) {
        log.info("{} got triggered. Message: {}", CommentCreatedListener.class, message.toString());
        CommentEventDto commentEventDto = message.getPayload();
        Optional<Comment> maybeComment = _commentRepository.findById(commentEventDto.getId());
        if (maybeComment.isPresent()) {
            return;
        }

        Optional<Book> maybeBook = _bookRepository.findById(commentEventDto.getBookId());
        if (maybeBook.isEmpty()) {
            return;
        }

        Comment newComment = _commentMapper.toEntity(commentEventDto);
        Comment savedComment = _commentRepository.save(newComment);

        Book book = maybeBook.get();
        book.getComments().add(savedComment.getId());
    }
}
