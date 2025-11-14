package com.joeyworks.cinnamon.task.service;

import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import com.joeyworks.cinnamon.fsm.enums.FsmDefinitionKey;
import com.joeyworks.cinnamon.fsm.service.FsmDefinitionService;
import com.joeyworks.cinnamon.fsm.service.FsmStateMachineService;
import com.joeyworks.cinnamon.task.dto.ProcessTaskDto;
import com.joeyworks.cinnamon.task.entity.ProcessTask;
import com.joeyworks.cinnamon.task.repository.ProcessTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * StateMachine과 비지니스 로직을 연결하기 위한 도메인
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessTaskService {

    private final FsmStateMachineService fsmStateMachineService;
    private final FsmDefinitionService fsmDefinitionService;
    private final ProcessTaskRepository  processTaskRepository;

    @Transactional
    public ProcessTask createTask(ProcessTaskDto processTaskDto, FsmDefinitionKey fsmDefinitionKey) throws Exception {
        //taskId가 비지니스로직과 fsm에서 사용됨. 관리용도와 생성로직을 간단하게 하기위해서 서버에서 직접 생성함.
        String taskId = Long.toString(processTaskDto.getId());

        //fsm 등록
        FsmDefinition fsmDefinition = fsmDefinitionService.getTheLatestDefinitionByDefinitionKey(fsmDefinitionKey.getDefinitionKey());

        //fsm 등록
        fsmStateMachineService.create(fsmDefinition.getId().toString(), taskId);

        //statemachine 조회
        FsmStateMachine fsmStateMachine = fsmStateMachineService.getFsmStateMachine(taskId);

        // task 등록
        return processTaskRepository.save(processTaskDto.toEntity(fsmDefinition, fsmStateMachine));
    }


}
