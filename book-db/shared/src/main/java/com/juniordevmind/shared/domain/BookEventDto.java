package com.juniordevmind.shared.domain;

import lombok.Data;

@Data
public class BookEventDto {
    private long id;
    private String title;
    private String description;
}
