package com.github.tarcalia.dmlabtestproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class PersistedEntity implements Serializable {
    public static String FIND_BY_ID = "findById";

    @CreationTimestamp
    @Column(name = "created_at", updatable=false)
    OffsetDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
    OffsetDateTime modifiedAt;
    @Version
    Long version;
}
