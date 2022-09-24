package com.juniordevmind.authorapi.models;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.juniordevmind.shared.models.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder(toBuilder = true)
@Table(name = "authors")
public class Author extends EntityBase {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    // https://stackoverflow.com/a/57241586/13723015
    @ElementCollection
    @CollectionTable(name = "author_books", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "book_id ")
    private List<String> books;
}
