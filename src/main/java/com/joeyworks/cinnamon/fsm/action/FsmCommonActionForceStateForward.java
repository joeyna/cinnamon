package com.joeyworks.cinnamon.fsm.action;

import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import com.joeyworks.cinnamon.fsm.enums.TransitionEvent;
import com.joeyworks.cinnamon.fsm.repository.FsmStateRepository;
import com.joeyworks.cinnamon.fsm.service.FsmStateMachineService;
import com.joeyworks.cinnamon.fsm.service.FsmStateService;
import com.joeyworks.cinnamon.fsm.service.FsmTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ForceStateForward")
@RequiredArgsConstructor
public class FsmCommonActionForceStateForward extends FsmBaseAction implements Action<FsmState, String> {

    private final FsmStateMachineService fsmStateMachineService;
    private final FsmTransitionService fsmTransitionService;


    @Override
    public void execute(StateContext<FsmState, String> context) {
        String stateMachineId = getStateMachineId(context);
        log.info("ForceStateForward Action machine Id: {}", stateMachineId);

        try {
            FsmState curr = getCurrentState(context);
            Long definitionId = Long.valueOf(getDefinitionId(context));


            FsmTransition fsmTransition = fsmTransitionService.getBySourceId(definitionId, curr.getId());

            fsmStateMachineService.sendEvent(stateMachineId, TransitionEvent.valueOf(fsmTransition.getEvent()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
