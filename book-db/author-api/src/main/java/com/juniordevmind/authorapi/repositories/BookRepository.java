package com.juniordevmind.authorapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniordevmind.authorapi.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
