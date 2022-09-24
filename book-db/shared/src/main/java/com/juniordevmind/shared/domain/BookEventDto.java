package com.juniordevmind.shared.domain;

import com.juniordevmind.shared.models.DtoBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookEventDto extends DtoBase {
    private String title;
    private String description;
}
