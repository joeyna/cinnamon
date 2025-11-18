package com.joeyworks.cinnamon.fsm.service;


import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import com.joeyworks.cinnamon.fsm.repository.FsmTransitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FsmTransitionService {

    private final FsmTransitionRepository transitionRepository;

    public FsmTransition getBySourceId(Long fsmDefinitionId, Long sourceId) {
        List<FsmTransition> transitions  =getByDefinition(fsmDefinitionId);
        return transitions.stream().filter(t-> sourceId.equals(t.getSource().getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("fsm_transition not found"));
    }

    public List<FsmTransition> getByDefinition(Long fsmDefinitionId) {
        return transitionRepository.findByFsmDefinitionId(fsmDefinitionId);
    }
}
