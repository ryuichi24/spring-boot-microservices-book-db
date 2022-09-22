package com.juniordevmind.authorapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniordevmind.authorapi.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
