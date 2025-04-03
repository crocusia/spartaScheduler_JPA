package com.example.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity { //공통 Entity
    //등록일
    @CreatedDate
    @Column(updatable = false) //수정되지 않음
    private LocalDateTime createdAt;
    //수정일
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
