package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fsm_definition")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FsmDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer version;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private String definitionKey;

    public static FsmDefinition of(Long id,  String name, Integer version, String description, LocalDateTime createdAt, String definitionKey) {
        return new FsmDefinition(id, name, version, description, createdAt, definitionKey);
    }

}