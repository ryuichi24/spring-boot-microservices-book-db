package com.juniordevmind.bookapi.listeners;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.juniordevmind.bookapi.models.Author;
import com.juniordevmind.bookapi.repositories.AuthorRepository;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.AuthorEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorCreatedListener {
    private final AuthorRepository _authorRepository;

    @RabbitListener(queues = RabbitMQKeys.BOOK_API_AUTHOR_CREATED_QUEUE)
    public void handleMessage(CustomMessage<AuthorEventDto> message) {
        log.info("{} got triggered. Message: {}", AuthorCreatedListener.class, message.toString());
        AuthorEventDto authorEventDto = message.getPayload();
        Optional<Author> result = _authorRepository.findById(authorEventDto.getId());
        if (result.isPresent()) {
            return;
        }
        Author newAuthor = new Author();
        newAuthor.setId(authorEventDto.getId());
        newAuthor.setName(authorEventDto.getName());
        newAuthor.setDescription(authorEventDto.getDescription());
        newAuthor.setCreatedAt(authorEventDto.getCreatedAt());
        newAuthor.setUpdatedAt(authorEventDto.getUpdatedAt());
        _authorRepository.save(newAuthor);
    }
}
