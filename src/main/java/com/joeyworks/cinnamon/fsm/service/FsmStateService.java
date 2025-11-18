package com.joeyworks.cinnamon.fsm.service;

import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.fsm.entity.FsmTransition;
import com.joeyworks.cinnamon.fsm.repository.FsmStateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FsmStateService {
    private final FsmStateRepository fsmStateRepository;
    private final FsmTransitionService fsmTransitionService;

    public FsmState getNextState(Long stateId) {
        FsmState state = getById(stateId);
        FsmTransition transition = fsmTransitionService.getBySourceId(state.getDefinitionId(), state.getId());
        Long targetId =  transition.getTarget().getId();
        return getById(targetId);
    }

    public FsmState getById(Long stateId) {
        return  fsmStateRepository.findById(stateId)
                .orElseThrow(() -> new EntityNotFoundException("fsm_state not found"));
    }
}
