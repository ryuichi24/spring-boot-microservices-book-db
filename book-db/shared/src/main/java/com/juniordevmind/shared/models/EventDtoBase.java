package com.juniordevmind.shared.models;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class EventDtoBase {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
