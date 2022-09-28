package com.juniordevmind.bookapi.dtos;

import java.util.UUID;

import com.juniordevmind.shared.models.DtoBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends DtoBase {
    private String content;
    private UUID bookId;
}
