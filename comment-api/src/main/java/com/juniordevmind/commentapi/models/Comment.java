package com.juniordevmind.commentapi.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
@Table(name = "comments")
public class Comment extends EntityBase {
    @Column(name = "content", nullable = false)
    private String content;
    @Type(type = "uuid-char")
    @Column(name = "book_id ")
    private UUID bookId;
}
