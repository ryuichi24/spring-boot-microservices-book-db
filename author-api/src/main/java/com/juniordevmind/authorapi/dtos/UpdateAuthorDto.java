package com.juniordevmind.authorapi.dtos;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateAuthorDto {
    @Length(max = 50)
    private String name;

    @Length(min = 1, max = 250)
    private String description;

    @Valid
    private List<@NotNull UUID> books;
}
