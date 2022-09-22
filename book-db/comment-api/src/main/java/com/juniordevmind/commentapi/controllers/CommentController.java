package com.juniordevmind.commentapi.controllers;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juniordevmind.commentapi.Services.CommentService;
import com.juniordevmind.commentapi.dtos.CreateCommentDto;
import com.juniordevmind.commentapi.dtos.UpdateCommentDto;
import com.juniordevmind.commentapi.models.Comment;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = CommentController.BASE_URL)
@RequiredArgsConstructor
public class CommentController {
    public static final String BASE_URL = "/api/v1/comments";

    private final CommentService _commentService;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Comment API is up and running!");
    }

    @GetMapping("")
    public ResponseEntity<List<Comment>> getComments() {
        return ResponseEntity.ok(_commentService.getComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable long id) {
        return ResponseEntity.ok(_commentService.getComment(id));
    }

    @PostMapping("")
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CreateCommentDto dto) {
        Comment newComment = _commentService.createComment(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newComment.getId()).toUri();
        return ResponseEntity.created(location).body(newComment);
    }

    // https://stackoverflow.com/questions/4088350/is-rest-delete-really-idempotent
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        _commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable long id, @Valid @RequestBody UpdateCommentDto dto) {
        _commentService.updateComment(dto, id);
        return ResponseEntity.noContent().build();
    }
}
