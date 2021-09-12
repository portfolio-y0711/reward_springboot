package com.portfolioy0711.api.data.entities;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
public class Base {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_at;
}
