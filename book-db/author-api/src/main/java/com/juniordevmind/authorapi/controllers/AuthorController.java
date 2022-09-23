package com.juniordevmind.authorapi.controllers;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.services.AuthorService;
import com.juniordevmind.shared.constants.RabbitMQKeys;
import com.juniordevmind.shared.domain.AuthorEventDto;
import com.juniordevmind.shared.models.CustomMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = AuthorController.BASE_URL)
@RequiredArgsConstructor
public class AuthorController {
    public static final String BASE_URL = "/api/v1/authors";

    private final AuthorService _authorService;
    private final RabbitTemplate _template;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Author API is up and running!");
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.ok(_authorService.getAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable long id) {
        return ResponseEntity.ok(_authorService.getAuthor(id));
    }

    @PostMapping("")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody CreateAuthorDto dto) {
        Author newAuthor = _authorService.createAuthor(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(newAuthor.getId()).toUri();
        CustomMessage<AuthorEventDto> msg = new CustomMessage<>();
        msg.setMessageId(UUID.randomUUID().toString());
        msg.setMessageDate(new Date());
        AuthorEventDto authorDto = new AuthorEventDto();
        authorDto.setId(newAuthor.getId());
        authorDto.setName(newAuthor.getName());
        authorDto.setDescription(newAuthor.getDescription());
        msg.setPayload(authorDto);
        _template.convertAndSend(RabbitMQKeys.AUTHOR_EXCHANGE, "", msg);
        return ResponseEntity.created(location).body(newAuthor);
    }

    // https://stackoverflow.com/questions/4088350/is-rest-delete-really-idempotent
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable long id) {
        _authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable long id, @Valid @RequestBody UpdateAuthorDto dto) {
        _authorService.updateAuthor(dto, id);
        return ResponseEntity.noContent().build();
    }
}
