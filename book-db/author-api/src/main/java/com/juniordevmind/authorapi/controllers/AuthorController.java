package com.juniordevmind.authorapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AuthorController.BASE_URL)
public class AuthorController {
    public static final String BASE_URL = "/api/v1/authors";

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Author API is up and running!");
    }

    @GetMapping("")
    public ResponseEntity<String> getAuthors() {
        return ResponseEntity.ok("getAuthors");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAuthor(@PathVariable long id) {
        return ResponseEntity.ok("getAuthor");
    }

    @PostMapping("")
    public ResponseEntity<String> createAuthor() {
        return ResponseEntity.ok("createAuthors");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable long id) {
        return ResponseEntity.ok("deleteAuthors");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable long id) {
        return ResponseEntity.ok("updateAuthors");
    }
}
