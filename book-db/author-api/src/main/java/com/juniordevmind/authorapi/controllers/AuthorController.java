package com.juniordevmind.authorapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = AuthorController.BASE_URL)
public class AuthorController {

    public static final String BASE_URL = "/api/v1/authors";

    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Author API is up and running!");
    }
}
