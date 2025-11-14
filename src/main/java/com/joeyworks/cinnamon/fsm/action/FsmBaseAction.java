package com.joeyworks.cinnamon.fsm.action;

import org.springframework.statemachine.StateContext;

public class FsmBaseAction {

    protected String getStateMachineId(StateContext<String, String> context){
        return context.getStateMachine().getId();
    }
    protected String getTaskId(StateContext<String, String> context){
        return context.getStateMachine().getId();
    }
}
