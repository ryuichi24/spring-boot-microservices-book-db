package com.juniordevmind.authorapi.dtos;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateAuthorDto {
    @Length(max = 50)
    private String name;

    @Length(min = 1, max = 250)
    private String description;
}
