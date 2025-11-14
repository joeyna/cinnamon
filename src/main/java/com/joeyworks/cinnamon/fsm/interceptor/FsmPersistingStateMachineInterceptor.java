/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joeyworks.cinnamon.fsm.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeyworks.cinnamon.fsm.repository.FsmStateMachineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.util.Assert;

@Slf4j
public class FsmPersistingStateMachineInterceptor<S, E, T> extends AbstractPersistingStateMachineInterceptor<S, E, T>
		implements StateMachineRuntimePersister<S, E, T> {

	private final FsmStateMachineRuntimePersister<S, E> persist;
    private final ObjectMapper objectMapper;

	public FsmPersistingStateMachineInterceptor(FsmStateMachineRepository fsmStateMachineRepository, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        Assert.notNull(fsmStateMachineRepository, "'jpaStateMachineRepository' must be set");
		this.persist = new FsmStateMachineRuntimePersister<S, E>(fsmStateMachineRepository);
	}

	public FsmPersistingStateMachineInterceptor(FsmStateMachineRuntimePersister<S, E> persist, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        Assert.notNull(persist, "'persist' must be set");
		this.persist = persist;
	}

	@Override
	public StateMachineInterceptor<S, E> getInterceptor() {
		return this;
	}

	@Override
	public void write(StateMachineContext<S, E> context, T contextObj) throws Exception {
        log.debug(objectMapper.writeValueAsString(context));
		persist.write(context, contextObj);
	}


    @Override
	public StateMachineContext<S, E> read(Object contextObj) throws Exception {
		return persist.read(contextObj);
	}
}
