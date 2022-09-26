package com.juniordevmind.commentapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniordevmind.commentapi.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    public List<Comment> findAllByBookId(UUID bookId);
}
