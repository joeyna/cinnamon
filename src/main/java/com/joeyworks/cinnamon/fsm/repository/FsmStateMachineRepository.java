package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import org.springframework.statemachine.data.StateMachineRepository;

public interface FsmStateMachineRepository extends StateMachineRepository<FsmStateMachine> {
}
