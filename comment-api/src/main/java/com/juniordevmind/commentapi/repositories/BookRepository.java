package com.juniordevmind.commentapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniordevmind.commentapi.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
