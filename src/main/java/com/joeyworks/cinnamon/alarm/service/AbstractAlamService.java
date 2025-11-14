package com.joeyworks.cinnamon.alarm.service;

import com.joeyworks.cinnamon.alarm.dto.AlarmDto;
import com.joeyworks.cinnamon.alarm.dto.AlarmHistoryDto;
import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.fsm.enums.FsmDefinitionKey;
import com.joeyworks.cinnamon.fsm.enums.TransitionEvent;
import com.joeyworks.cinnamon.fsm.service.FsmStateMachineService;
import com.joeyworks.cinnamon.task.dto.ProcessTaskDto;
import com.joeyworks.cinnamon.task.entity.ProcessTask;
import com.joeyworks.cinnamon.task.service.ProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractAlamService<T extends AlarmDto> {

    @Autowired
    private AlarmDefinitionService definitionService;
    @Autowired
    private AlarmHistoryService alarmHistoryService;
    @Autowired
    private ProcessTaskService processTaskService;
    @Autowired
    private FsmStateMachineService fsmStateMachineService;

    /**
     * 수신된 데이터에서 eventKey 파싱
     * evnetKey가 존재하지 않거나 정의된 eventKey가 아닐경우 이후 진행 하지 않음.
     * event 수신 방식마다 데이터 규격이 다르기 때문에 각자 구현.
     * @param dto
     * @return
     */
    protected abstract String parseEventKey(T dto);

    /**
     * 수신된 데이터를 AlarmHistory 형식에 맞춰서 매핑
     * event 수신 방식마다 데이터 규격이 다르기 때문에 각자 구현.
     * @param dto
     * @param alarm
     * @param task
     * @return
     */
    protected abstract AlarmHistoryDto mapToHistoryDto(T dto, AlarmDefinition alarm, ProcessTask task);


    protected void executeCreateAlarm(T dto, FsmDefinitionKey fsmDefinitionKey) throws Exception {
        //관리중인 알람만 처리,  등록되지 않은 알람은 무시.
        String eventKey = parseEventKey(dto);
        if(eventKey == null) return;
        AlarmDefinition alarm = definitionService.getByEventKey(eventKey);
        if(alarm == null) return;

        //ProcessTask 등록.
        ProcessTask task = processTaskService.createTask(ProcessTaskDto.from(dto), fsmDefinitionKey);

        //알람 발생이력 저장
        AlarmHistoryDto historyDto = mapToHistoryDto(dto, alarm, task);
        alarmHistoryService.save(historyDto);


        //다음 단계로 이동.
        // TODO 비지니스 로직은 statemachine을 몰랐으면 함. 트랜젝션 밖에서 설정으로 다음 스테이트로 보낼 수 있는 방법을 찾아봐야할듯.
        fsmStateMachineService.sendEvent(task.getId().toString(), TransitionEvent.EVENT_CONFIRM);
    }
}
