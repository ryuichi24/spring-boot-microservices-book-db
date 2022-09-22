package com.juniordevmind.authorapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniordevmind.authorapi.dtos.CreateAuthorDto;
import com.juniordevmind.authorapi.dtos.UpdateAuthorDto;
import com.juniordevmind.authorapi.models.Author;
import com.juniordevmind.authorapi.services.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = AuthorController.BASE_URL)
@RequiredArgsConstructor
public class AuthorController {
    public static final String BASE_URL = "/api/v1/authors";

    private final AuthorService _authorService;

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
        return ResponseEntity.ok(_authorService.createAuthor(dto));
    }

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
