package com.juniordevmind.bookapi.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateBookDto {
    @NotNull
    @Length(max = 50)
    private String title;

    @NotNull
    @Length(min = 1, max = 250)
    private String description;
}
