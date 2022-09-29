package com.juniordevmind.bookapi.dtos;

import com.juniordevmind.shared.models.DtoBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto extends DtoBase {
    private String name;
    private String description;
}
