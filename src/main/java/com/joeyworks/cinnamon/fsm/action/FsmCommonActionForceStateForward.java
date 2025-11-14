package com.joeyworks.cinnamon.fsm.action;

import com.joeyworks.cinnamon.fsm.enums.TransitionEvent;
import com.joeyworks.cinnamon.fsm.service.FsmStateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ForceStateForward")
@RequiredArgsConstructor
public class FsmCommonActionForceStateForward extends FsmBaseAction implements Action<String, String> {

    private final FsmStateMachineService fsmStateMachineService;

    @Override
    public void execute(StateContext<String, String> context) {
        String stateMachineId = getStateMachineId(context);
        log.info("ForceStateForward Action machine Id: {}", stateMachineId);

        try {
            fsmStateMachineService.sendEvent(stateMachineId, TransitionEvent.EVENT_CONFIRM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
