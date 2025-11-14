package com.joeyworks.cinnamon.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AlarmDto {
    private LocalDateTime createdAt;
}
