package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmState;
import org.springframework.statemachine.data.StateRepository;

import java.util.List;

public interface FsmStateRepository extends StateRepository<FsmState> {
    List<FsmState> findByState(String state);
}
