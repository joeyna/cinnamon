package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import org.springframework.statemachine.data.TransitionRepository;

public interface FsmTransitionRepository extends TransitionRepository<FsmTransition> {
}
