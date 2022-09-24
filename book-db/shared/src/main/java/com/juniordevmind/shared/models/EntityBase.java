package com.juniordevmind.shared.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public class EntityBase {
    // https://qiita.com/KevinFQ/items/a6d92ec7b32911e50ffe
    // https://stackoverflow.com/q/66936394/13723015
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void PrePersist() {
        if (Objects.isNull(this.id))
            this.id = UUID.randomUUID();
        if (Objects.isNull(this.createdAt))
            this.createdAt = LocalDateTime.now();
        if (Objects.isNull(this.updatedAt))
            this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}