package com.juniordevmind.shared.domain;

import com.juniordevmind.shared.models.EventDtoBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorEventDto extends EventDtoBase {
    private String name;
    private String description;
}
