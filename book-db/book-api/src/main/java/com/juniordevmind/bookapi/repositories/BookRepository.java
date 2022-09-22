package com.juniordevmind.bookapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniordevmind.bookapi.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
