package com.juniordevmind.commentapi.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateCommentDto {
    @NotNull
    @Length(max = 300)
    private String content;
}
