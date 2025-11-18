package com.joeyworks.cinnamon.fsm.action;

import com.joeyworks.cinnamon.fsm.entity.FsmState;
import org.springframework.statemachine.StateContext;

public class FsmBaseAction {

    protected String getStateMachineId(StateContext<FsmState, String> context){
//        return context.getExtendedState().getVariables().get("");
        return context.getStateMachine().getId();
    }
    protected String getTaskId(StateContext<FsmState, String> context){
//        return context.getExtendedState().getVariables().get("taskId");
        return context.getStateMachine().getId();
    }
    protected String getDefinitionId(StateContext<FsmState, String> context){
        return context.getExtendedState().getVariables().get("definitionId").toString();
    }

    protected FsmState getCurrentState(StateContext<FsmState, String> context){
        return context.getStateMachine().getState().getId();
    }
}
