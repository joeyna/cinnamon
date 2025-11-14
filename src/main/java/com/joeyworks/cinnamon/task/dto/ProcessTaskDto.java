package com.joeyworks.cinnamon.task.dto;

import com.joeyworks.cinnamon.alarm.dto.AlarmDto;
import com.joeyworks.cinnamon.common.vo.Dates;
import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import com.joeyworks.cinnamon.synology.dto.SynologyAlarmWebhookDto;
import com.joeyworks.cinnamon.task.entity.ProcessTask;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ProcessTaskDto {
    private Long id;
    private String name;
    private FsmDefinition fsmDefinition;
    private FsmStateMachine fsmStateMachine;
    private Dates dates;

    public ProcessTask toEntity() {
        return ProcessTask.of(id, name, fsmDefinition, fsmStateMachine, dates);
    }

    public ProcessTask toEntity(FsmDefinition fsmDefinition, FsmStateMachine fsmStateMachine) {
        return ProcessTask.of(this.id, this.name, fsmDefinition, fsmStateMachine, this.dates);
    }

    public static ProcessTaskDto from(AlarmDto alarmDto) {
        long taskId = System.currentTimeMillis();

        //TODO 케이스 늘어나면 리팩토링.
        String name = null;
        if(alarmDto instanceof SynologyAlarmWebhookDto)
            name = "Synology Webhook Alarm Occurred";

        return ProcessTaskDto.builder()
                .id(taskId)
                .name(name)
                .dates(Dates.create())
                .build();
    }
}
