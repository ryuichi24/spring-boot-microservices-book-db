package com.juniordevmind.authorapi.models;

import javax.persistence.Entity;

import com.juniordevmind.shared.models.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Author extends EntityBase {
    private long id;
    private String name; 
    private String description;
}
