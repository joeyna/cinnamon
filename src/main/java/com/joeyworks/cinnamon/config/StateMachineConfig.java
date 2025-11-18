package com.joeyworks.cinnamon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeyworks.cinnamon.fsm.entity.FsmState;
import com.joeyworks.cinnamon.fsm.interceptor.FsmPersistingStateMachineInterceptor;
import com.joeyworks.cinnamon.fsm.interceptor.FsmStateMachineRuntimePersister;
import com.joeyworks.cinnamon.fsm.repository.FsmStateMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {

    private final ObjectMapper objectMapper;

    @Bean
    public StateMachineRuntimePersister<FsmState, String, String> stateMachineRuntimePersister(FsmStateMachineRepository fsmStateMachineRepository) {
        return new FsmPersistingStateMachineInterceptor<>(new FsmStateMachineRuntimePersister<>(fsmStateMachineRepository),  objectMapper);
    }
}
