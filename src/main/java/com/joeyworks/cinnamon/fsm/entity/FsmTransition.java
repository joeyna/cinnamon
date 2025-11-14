package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.data.RepositoryTransition;
import org.springframework.statemachine.transition.TransitionKind;

import java.util.Set;

@Entity
@Table(name = "fsm_transition")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FsmTransition extends RepositoryTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "machine_id")
    private String machineId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_transition_source"))
    private FsmState source;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_transition_target"))
    private FsmState target;

    @Column(name = "event")
    private String event;

    @Column(name = "kind")
    private TransitionKind kind;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(foreignKey = @ForeignKey(name = "fk_transition_actions_t"), inverseForeignKey = @ForeignKey(name = "fk_transition_actions_a"))
    private Set<FsmAction> actions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_transition_guard"))
    private FsmGuard guard;

    /**
     * Instantiates a new jpa repository transition.
     */
    public FsmTransition() {
        this(null, null, null);
    }

    /**
     * Instantiates a new jpa repository transition.
     *
     * @param source the source
     * @param target the target
     * @param event  the event
     */
    public FsmTransition(FsmState source, FsmState target, String event) {
        this(null, source, target, event);
    }

    /**
     * Instantiates a new jpa repository transition.
     *
     * @param machineId the machine id
     * @param source    the source
     * @param target    the target
     * @param event     the event
     */
    public FsmTransition(String machineId, FsmState source, FsmState target, String event) {
        this(machineId, source, target, event, null);
    }

    /**
     * Instantiates a new jpa repository transition.
     *
     * @param machineId the machine id
     * @param source    the source
     * @param target    the target
     * @param event     the event
     * @param actions   the actions
     */
    public FsmTransition(String machineId, FsmState source, FsmState target, String event, Set<FsmAction> actions) {
        this.machineId = machineId == null ? "" : machineId;
        this.source = source;
        this.target = target;
        this.event = event;
        this.actions = actions;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public FsmState getSource() {
        return source;
    }

    public void setSource(FsmState source) {
        this.source = source;
    }

    @Override
    public FsmState getTarget() {
        return target;
    }

    public void setTarget(FsmState target) {
        this.target = target;
    }

    @Override
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public Set<FsmAction> getActions() {
        return actions;
    }

    public void setActions(Set<FsmAction> actions) {
        this.actions = actions;
    }

    @Override
    public FsmGuard getGuard() {
        return guard;
    }

    public void setGuard(FsmGuard guard) {
        this.guard = guard;
    }

    @Override
    public TransitionKind getKind() {
        return kind;
    }

    public void setKind(TransitionKind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "FsmTransition [id=" + id + ", machineId=" + machineId + ", source=" + source + ", target=" + target + ", event="
                + event + ", kind=" + kind + ", actions=" + actions + ", guard=" + guard + "]";
    }


    public static FsmTransition ofInitial(String machineId, FsmState source, FsmState target, String event) {
        return new FsmTransition(machineId, source, target, event);
    }
}
