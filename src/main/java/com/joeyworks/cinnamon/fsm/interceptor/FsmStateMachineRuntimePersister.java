package com.joeyworks.cinnamon.fsm.interceptor;

import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import com.joeyworks.cinnamon.fsm.repository.FsmStateMachineRepository;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.data.RepositoryStateMachinePersist;
import org.springframework.statemachine.data.StateMachineRepository;
import org.springframework.statemachine.service.StateMachineSerialisationService;

public class FsmStateMachineRuntimePersister<S, E> extends RepositoryStateMachinePersist<FsmStateMachine, S, E> {
    private final FsmStateMachineRepository fsmStateMachineRepository;

    public FsmStateMachineRuntimePersister(FsmStateMachineRepository fsmStateMachineRepository) {
        super();
        this.fsmStateMachineRepository = fsmStateMachineRepository;
    }

    public FsmStateMachineRuntimePersister(FsmStateMachineRepository fsmStateMachineRepository,
                                           StateMachineSerialisationService<S, E> serialisationService) {
        super(serialisationService);
        this.fsmStateMachineRepository = fsmStateMachineRepository;
    }

    @Override
    protected StateMachineRepository<FsmStateMachine> getRepository() {
        return fsmStateMachineRepository;
    }

    @Override
    protected FsmStateMachine build(StateMachineContext<S, E> context, Object contextObj, byte[] serialisedContext) {
        return FsmStateMachine.of(context.getId(), context.getState() != null ? context.getState().toString() : null, serialisedContext);
    }
}
