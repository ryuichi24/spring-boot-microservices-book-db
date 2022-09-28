package com.juniordevmind.bookapi.listeners;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class CommentDeletedListener {
    private final BookRepository _bookRepository;
    private final CommentRepository _commentRepository;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.BOOK_API_COMMENT_DELETED_QUEUE)
    public void handleMessage(CustomMessage<CommentEventDto> message) {
        log.info("{} got triggered. Message: {}", CommentDeletedListener.class, message.toString());
        CommentEventDto commentEventDto = message.getPayload();
        Optional<Comment> maybeComment = _commentRepository.findById(commentEventDto.getId());
        if (maybeComment.isEmpty()) {
            return;
        }

        Comment comment = maybeComment.get();
        Optional<Book> maybeBook = _bookRepository.findById(comment.getBookId());
        if (maybeBook.isEmpty()) {
            return;
        }

        Book book = maybeBook.get();

        List<UUID> filtered = book.getComments().stream().filter(commentId -> !commentId.equals(comment.getId()))
                .toList();
        // https://www.baeldung.com/spring-data-crud-repository-save
        book.setComments(filtered);

        _commentRepository.delete(comment);
    }
}
