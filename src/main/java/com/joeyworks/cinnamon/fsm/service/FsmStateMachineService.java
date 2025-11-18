package com.joeyworks.cinnamon.fsm.service;

import com.joeyworks.cinnamon.fsm.entity.FsmAction;
import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import com.joeyworks.cinnamon.fsm.enums.TransitionEvent;
import com.joeyworks.cinnamon.fsm.repository.FsmStateMachineRepository;
import com.joeyworks.cinnamon.fsm.repository.FsmStateRepository;
import com.joeyworks.cinnamon.fsm.repository.FsmTransitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.PseudoStateKind;
import org.springframework.statemachine.support.AbstractStateMachine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FsmStateMachineService {

    //StateMachine.machineId는 PK이고 다른 blue print 테이블에서의 machineId는 grouping 성격이 강함. 개발 편의상 명확한 구분을 위해서 Definition 개념을 추가. state, transition의 machindId와 매핑.
    final String CONTEXT_EXTENDED_STATE_KEY_DEFINITION = "definitionId";
    final String CONTEXT_EXTENDED_STATE_KEY_BUSINESS_ID = "taskId";

    private final FsmStateRepository stateRepository;
    private final FsmTransitionRepository transitionRepository;

    private final FsmStateMachineRepository stateMachineRepository;
    private final StateMachineRuntimePersister<FsmState, String, String> persister;

    private final ApplicationContext applicationContext;


    public FsmStateMachine getFsmStateMachine(String id) {
        return stateMachineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("fsm_state_machine not found"));
    }

    public void sendEvent(String businessId, TransitionEvent event) throws Exception {
        log.info("FsmStateMachineService::sendEvent - businessId : {}, event : {}", businessId, event);
        StateMachine<FsmState, String> sm = restore(businessId);

        Message<String> msg = MessageBuilder
                .withPayload(event.name())
                .build();
        sm.sendEvent(Mono.just(msg)).blockFirst();
    }


    /**
     * DB에 저장된 실행 인스턴스 복원
     *
     * @param businessId
     * @return
     * @throws Exception
     */
    public StateMachine<FsmState, String> restore(String businessId) throws Exception {
        StateMachineContext<FsmState, String> context = persister.read(businessId);

        if (context == null)
            throw new RuntimeException("context is incorrect :: CONTEXT NOT FOUND : " + businessId);

        //비지니스 로직과 fsm_definition 매핑 정보로 defnifitionId를 구하는 방법이 정석으로 보임. 현재는 context에서 조회중.
        String definitionId = context.getExtendedState().getVariables().get(CONTEXT_EXTENDED_STATE_KEY_DEFINITION).toString();
        if (definitionId == null)
            throw new RuntimeException("context is incorrect :: FLOW ID NOT FOUND : " + businessId);

        StateMachine<FsmState, String> sm = build(definitionId);


        //인터셉터, context 등록.
        sm.getStateMachineAccessor().doWithAllRegions(accessor -> {
            accessor.addStateMachineInterceptor(persister.getInterceptor());
            accessor.resetStateMachineReactively(context).block();
        });

        //statemachine 재개
        sm.startReactively().block();
        return sm;
    }


    /**
     * StateMachine 최초 생성 , machineId (FlowId), bussinessId(process_task_id)
     *
     * @param definitionId
     * @param businessId
     * @return
     * @throws Exception
     */
    public StateMachine<FsmState, String> create(String definitionId, String businessId) throws Exception {

        StateMachine<FsmState, String> sm = build(definitionId);

        //StateMachineID 세팅
        if (sm instanceof AbstractStateMachine<FsmState, String>)
            ((AbstractStateMachine<FsmState, String>) sm).setId(businessId);

        //인터셉터 등록
        sm.getStateMachineAccessor()
                .doWithAllRegions(accessor -> accessor.addStateMachineInterceptor(persister.getInterceptor()));

        //businessId 저장.
        sm.getExtendedState().getVariables()
                .put(CONTEXT_EXTENDED_STATE_KEY_BUSINESS_ID, businessId); //InstanceId

        //definitionId 저장.
        sm.getExtendedState().getVariables()
                .put(CONTEXT_EXTENDED_STATE_KEY_DEFINITION, definitionId); //definitionId

        //statemachine 초기상태로 진입
        sm.startReactively().block();

        return sm;
    }


    private StateMachine<FsmState, String> build(String definitionId) throws Exception {
        List<FsmState> states = stateRepository.findByMachineId(definitionId);
        List<FsmTransition> transitions = transitionRepository.findByMachineId(definitionId);

        StateMachineBuilder.Builder<FsmState, String> builder = StateMachineBuilder.builder();

        // State
        var stateConfig = builder.configureStates().withStates();
        for (FsmState s : states) {
            if (s.isInitial() != null && s.isInitial()) stateConfig.initial(s);
            if (PseudoStateKind.END.equals(s.getKind())) stateConfig.end(s);
            stateConfig.state(s);

            //entry action
            for (FsmAction actions : s.getEntryActions()) {
                resolveActionOptional(actions.getName())
                        .ifPresent(action -> stateConfig.stateEntry(s, action));
            }

            //exit action
            for (FsmAction actions : s.getExitActions()) {
                resolveActionOptional(actions.getName())
                        .ifPresent(action -> stateConfig.stateExit(s, action));
            }

            //state action
            for (FsmAction actions : s.getStateActions()) {
                resolveActionOptional(actions.getName())
                        .ifPresent(action -> stateConfig.state(s, action));
            }
        }

        // Transition
        var transitionsConfig = builder.configureTransitions();
        for (FsmTransition t : transitions) {
            var transition = transitionsConfig.withExternal()
                    .source(t.getSource())
                    .target(t.getTarget())
                    .event(t.getEvent());

            //transition action
            for (FsmAction actionEntity : t.getActions()) {
                resolveActionOptional(actionEntity.getName())
                        .ifPresent(transition::action);
            }
        }

        //build
        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private Optional<Action<FsmState, String>> resolveActionOptional(String beanName) {
        if (!applicationContext.containsBean(beanName)) {
            return Optional.empty();
        }
        Object bean = applicationContext.getBean(beanName);
        if (bean instanceof Action) {
            return Optional.of((Action<FsmState, String>) bean);
        } else {
            return Optional.empty();
        }
    }
}
