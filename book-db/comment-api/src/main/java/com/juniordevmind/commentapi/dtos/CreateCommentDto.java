package com.juniordevmind.commentapi.dtos;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateCommentDto {
    @NotNull
    @Length(max = 300)
    private String content;
    @NotNull
    private UUID bookId;
}
