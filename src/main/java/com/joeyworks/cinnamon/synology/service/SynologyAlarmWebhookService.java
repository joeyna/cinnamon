package com.joeyworks.cinnamon.synology.service;

import com.joeyworks.cinnamon.alarm.dto.AlarmHistoryDto;
import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.alarm.service.AbstractAlamService;
import com.joeyworks.cinnamon.alarm.service.Alarm;
import com.joeyworks.cinnamon.fsm.enums.FsmDefinitionKey;
import com.joeyworks.cinnamon.synology.dto.SynologyAlarmWebhookDto;
import com.joeyworks.cinnamon.task.entity.ProcessTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SynologyAlarmWebhookService extends AbstractAlamService<SynologyAlarmWebhookDto> implements Alarm<SynologyAlarmWebhookDto>{

    @Override
    protected String parseEventKey(SynologyAlarmWebhookDto dto) {
        //Webhook으로 수신하는 event는 전부 처리한다.
        return "Synology Webhook";
    }

    @Override
    protected AlarmHistoryDto mapToHistoryDto(SynologyAlarmWebhookDto dto, AlarmDefinition alarm, ProcessTask task) {
        return AlarmHistoryDto.from(dto, alarm, task);
    }

    @Override
    @Transactional
    public void createAlarm(SynologyAlarmWebhookDto dto) throws Exception {
        executeCreateAlarm(dto, FsmDefinitionKey.SYNOLOGY_ALARM_WEBHOOK);
    }
}
