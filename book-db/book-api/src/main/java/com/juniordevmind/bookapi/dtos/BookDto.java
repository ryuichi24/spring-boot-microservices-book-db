package com.juniordevmind.bookapi.dtos;

import java.util.List;
import java.util.UUID;

import com.juniordevmind.shared.models.DtoBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends DtoBase {
    private String title;
    private String description;
    private List<AuthorDto> authorList;
    private List<UUID> authorIdList;
}
