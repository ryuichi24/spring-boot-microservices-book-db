package com.juniordevmind.shared.domain;

import java.util.UUID;

import com.juniordevmind.shared.models.DtoBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentEventDto extends DtoBase {
    private String content;
    private UUID bookId;
}
