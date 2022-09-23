package com.juniordevmind.shared.domain;

import lombok.Data;

@Data
public class AuthorEventDto {
    private long id;
    private String name;
    private String description;
}
