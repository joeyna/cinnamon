package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import org.springframework.statemachine.data.TransitionRepository;

import java.util.List;

public interface FsmTransitionRepository extends TransitionRepository<FsmTransition> {
    List<FsmTransition> findByFsmDefinitionId(Long fsmDefinitionId);
}
