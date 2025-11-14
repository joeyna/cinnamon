package com.joeyworks.cinnamon.alarm.dto;

import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.common.vo.Dates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AlarmDefinitionDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String eventKey;
    private Dates dates;

    public static AlarmDefinitionDto from(AlarmDefinition alarmDefinition) {
        return AlarmDefinitionDto.builder()
                .id(alarmDefinition.getId())
                .name(alarmDefinition.getName())
                .type(alarmDefinition.getType())
                .description(alarmDefinition.getDescription())
                .eventKey(alarmDefinition.getEventKey())
                .dates(alarmDefinition.getDates())
                .build();
    }

    public AlarmDefinition toEntity() {
        return AlarmDefinition.of(id, name, type, eventKey, description, dates);
    }
}
