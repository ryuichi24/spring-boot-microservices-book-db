package com.juniordevmind.bookapi.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.juniordevmind.bookapi.mappers.CommentMapper;
import com.juniordevmind.bookapi.models.Comment;
import com.juniordevmind.bookapi.repositories.CommentRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.CommentEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentUpdatedListener {
    private final CommentRepository _commentRepository;
    private final CommentMapper _commentMapper;

    @Transactional()
    @RabbitListener(queues = RabbitMQKeys.BOOK_API_COMMENT_UPDATED_QUEUE)
    public void handleMessage(CustomMessage<CommentEventDto> message) {
        log.info("{} got triggered. Message: {}", CommentUpdatedListener.class, message.toString());
        CommentEventDto commentEventDto = message.getPayload();
        Comment newComment = _commentMapper.toEntity(commentEventDto);
        _commentRepository.save(newComment);
    }
}
