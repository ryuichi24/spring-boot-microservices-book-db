package com.juniordevmind.bookapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juniordevmind.bookapi.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
