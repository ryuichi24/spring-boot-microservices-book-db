package com.juniordevmind.shared.domain;

import com.juniordevmind.shared.models.DtoBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorEventDto extends DtoBase {
    private String name;
    private String description;
}
