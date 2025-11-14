package com.joeyworks.cinnamon.alarm.dto;

import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.alarm.entity.AlarmHistory;
import com.joeyworks.cinnamon.common.vo.Dates;
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
public class AlarmHistoryDto {
    private Long id;
    private String msg;
    private AlarmDefinition definition;
    private ProcessTask task;
    private Dates dates;

    public AlarmHistory toEntity() {
        return AlarmHistory.of(id, msg, definition, task,dates);
    }

    public static AlarmHistoryDto from(AlarmHistory alarmHistory) {
        return AlarmHistoryDto.builder()
                .id(alarmHistory.getId())
                .msg(alarmHistory.getMsg())
                .definition(alarmHistory.getDefinition())
                .task(alarmHistory.getTask())
                .dates(alarmHistory.getDates())
                .build();
    }

    public static AlarmHistoryDto from(SynologyAlarmWebhookDto dto, AlarmDefinition alarm, ProcessTask task) {
        return AlarmHistoryDto.builder()
                .msg(dto.getText())
                .definition(alarm)
                .task(task)
                .dates(Dates.create())
                .build();
    }
}
