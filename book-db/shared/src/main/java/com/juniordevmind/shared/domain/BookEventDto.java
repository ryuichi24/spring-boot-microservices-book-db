package com.juniordevmind.shared.domain;

import com.juniordevmind.shared.models.EventDtoBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookEventDto extends EventDtoBase {
    private String title;
    private String description;
}
