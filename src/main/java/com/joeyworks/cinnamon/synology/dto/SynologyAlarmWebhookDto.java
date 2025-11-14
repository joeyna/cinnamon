package com.joeyworks.cinnamon.synology.dto;

import com.joeyworks.cinnamon.alarm.dto.AlarmDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SynologyAlarmWebhookDto extends AlarmDto {
    private String text;
}
