package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmState;
import org.springframework.statemachine.data.StateRepository;

public interface FsmStateRepository extends StateRepository<FsmState> {
}
