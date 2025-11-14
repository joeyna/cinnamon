package com.joeyworks.cinnamon.alarm.entity;

import com.joeyworks.cinnamon.common.vo.Dates;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm_definition")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(unique = true)
    private String eventKey;

    @Embedded
    private Dates dates;

    public static AlarmDefinition of(Long id, String name, String type, String description, String eventKey, Dates dates) {
        if (dates == null) dates = Dates.create();

        return new AlarmDefinition(id, name, type, description, eventKey, dates);
    }
}