package com.juniordevmind.commentapi.listeners;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.juniordevmind.commentapi.models.Comment;
import com.juniordevmind.commentapi.repositories.BookRepository;
import com.juniordevmind.commentapi.repositories.CommentRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.BookEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookDeletedListener {
    private final BookRepository _bookRepository;
    private final CommentRepository _commentRepository;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.COMMENT_API_BOOK_DELETED_QUEUE)
    public void handleMessage(CustomMessage<BookEventDto> message) {
        log.info("{} got triggered. Message: {}", BookDeletedListener.class, message.toString());
        BookEventDto bookEventDto = message.getPayload();
        List<Comment> comments = _commentRepository.findAllByBookId(bookEventDto.getId());
        if (comments.size() < 1) {
            return;
        }

        for (Comment commentItem : comments) {
            _commentRepository.delete(commentItem);
        }

        _bookRepository.deleteById(bookEventDto.getId());
    }
}
