package com.juniordevmind.shared.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class EventDtoBase {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
