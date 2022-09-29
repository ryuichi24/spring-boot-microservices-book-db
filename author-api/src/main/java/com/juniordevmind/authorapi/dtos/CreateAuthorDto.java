package com.juniordevmind.authorapi.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateAuthorDto {
    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 250)
    private String description;

    // https://b1san-blog.com/post/spring/spring-validation/
    // https://qiita.com/miriwo/items/8ea80aebd113edafebb0
    private List<UUID> books;
}
