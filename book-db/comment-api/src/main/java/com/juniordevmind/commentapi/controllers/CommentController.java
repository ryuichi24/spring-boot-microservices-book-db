package com.juniordevmind.commentapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CommentController.BASE_URL)
public class CommentController {
    public static final String BASE_URL = "/api/v1/comments";

    @GetMapping("")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Comment API is up and running!");
    }
}
