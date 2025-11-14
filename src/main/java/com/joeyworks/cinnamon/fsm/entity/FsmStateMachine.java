package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.statemachine.data.RepositoryStateMachine;

@Entity
@Table(name = "fsm_state_machine")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FsmStateMachine extends RepositoryStateMachine {
    @Id
    @Column(name = "machine_id")
    private String machineId;

    @Column(name = "state")
    private String state;

    @Lob
    @Column(name = "state_machine_context", length = 10240)
    private byte[] stateMachineContext;

    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public byte[] getStateMachineContext() {
        return stateMachineContext;
    }

    public void setStateMachineContext(byte[] stateMachineContext) {
        this.stateMachineContext = stateMachineContext;
    }

    public static FsmStateMachine of(String machineId, String state,byte[] stateMachineContext) {
        return new FsmStateMachine(machineId, state, stateMachineContext);
    }

}
