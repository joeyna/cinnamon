package com.joeyworks.cinnamon.task.entity;

import com.joeyworks.cinnamon.common.vo.Dates;
import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import com.joeyworks.cinnamon.fsm.entity.FsmStateMachine;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "process_task")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessTask {
    @Id
    private Long id;

    private String name;

   @ManyToOne
   @JoinColumn(name = "fsm_definition_id", foreignKey = @ForeignKey(name = "fk_process_task_fsm_definition"))
   private FsmDefinition fsmDefinition;

    @ManyToOne
    @JoinColumn(name = "fsm_state_machine_machine_id", foreignKey = @ForeignKey(name = "fk_process_task_fsm_state_machine"))
    private FsmStateMachine fsmStateMachine;

    @Embedded
    private Dates dates;

    public static ProcessTask of(Long id, String name, FsmDefinition fsmDefinition, FsmStateMachine fsmStateMachine, Dates dates) {
        return new ProcessTask(id, name, fsmDefinition, fsmStateMachine, dates);
    }
}