package com.juniordevmind.commentapi.dtos;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UpdateCommentDto {
    @Length(max = 300)
    private String content;
}
