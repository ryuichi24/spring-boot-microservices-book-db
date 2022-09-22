package com.juniordevmind.authorapi.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UpdateAuthorDto {
    @NotNull
    @Length(max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 250)
    private String description;
}
