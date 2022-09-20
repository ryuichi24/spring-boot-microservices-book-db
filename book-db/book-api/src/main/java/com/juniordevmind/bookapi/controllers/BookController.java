package com.juniordevmind.bookapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = BookController.BASE_URL)
public class BookController {
    public static final String BASE_URL = "/api/v1/books";

    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Book API is up and running!");
    }
}
