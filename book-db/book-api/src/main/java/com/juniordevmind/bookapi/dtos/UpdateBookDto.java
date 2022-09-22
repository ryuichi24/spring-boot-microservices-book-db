package com.juniordevmind.bookapi.dtos;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateBookDto {
    @Length(max = 50)
    private String title;

    @Length(min = 1, max = 250)
    private String description;
}
