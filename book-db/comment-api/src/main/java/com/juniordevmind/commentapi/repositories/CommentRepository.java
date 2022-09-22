package com.juniordevmind.commentapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juniordevmind.commentapi.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
