package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmGuard;
import org.springframework.statemachine.data.GuardRepository;

public interface FsmGuardRepository extends GuardRepository<FsmGuard> {
}
