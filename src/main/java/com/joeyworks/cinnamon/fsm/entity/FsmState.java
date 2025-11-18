package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.statemachine.data.RepositoryAction;
import org.springframework.statemachine.data.RepositoryState;
import org.springframework.statemachine.state.PseudoStateKind;

import java.util.Set;

@Entity
@Table(name = "fsm_state")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FsmState extends RepositoryState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "machine_id")
    private String machineId;

    @Column(name = "state")
    private String state;

    @Column(name = "region")
    private String region;

    @Column(name = "initial_state")
    private Boolean initial;

    @Column(name = "kind")
    private PseudoStateKind kind;

    @Column(name = "submachine_id")
    private String submachineId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_state_initial_action"))
    private FsmAction initialAction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_state_parent_state"))
    private FsmState parentState;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(foreignKey = @ForeignKey(name = "fk_state_state_actions_s"), inverseForeignKey = @ForeignKey(name = "fk_state_state_actions_a"))
    private Set<FsmAction> stateActions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(foreignKey = @ForeignKey(name = "fk_state_entry_actions_s"), inverseForeignKey = @ForeignKey(name = "fk_state_entry_actions_a"))
    private Set<FsmAction> entryActions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(foreignKey = @ForeignKey(name = "fk_state_exit_actions_s"), inverseForeignKey = @ForeignKey(name = "fk_state_exit_actions_a"))
    private Set<FsmAction> exitActions;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @CollectionTable(name = "deferred_events", foreignKey = @ForeignKey(name = "fk_state_deferred_events"))
    private Set<String> deferredEvents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "definition_id", foreignKey = @ForeignKey(name = "fk_fsm_state_definition"))
    private FsmDefinition fsmDefinition;

    /**
     * Instantiates a new jpa repository state.
     */
    public FsmState() {
        this(null);
    }

    /**
     * Instantiates a new jpa repository state.
     *
     * @param state the state
     */
    public FsmState(String state) {
        this(state, false);
    }

    /**
     * Instantiates a new jpa repository state.
     *
     * @param state   the state
     * @param initial the initial
     */
    public FsmState(String state, Boolean initial) {
        this(null, state, initial);
    }

    /**
     * Instantiates a new jpa repository state.
     *
     * @param machineId the machine id
     * @param state     the state
     * @param initial   the initial
     */
    public FsmState(String machineId, String state, Boolean initial) {
        this(machineId, null, state, initial);
    }

    /**
     * Instantiates a new jpa repository state.
     *
     * @param machineId   the machine id
     * @param parentState the parent state
     * @param state       the state
     * @param initial     the initial
     */
    public FsmState(String machineId, FsmState parentState, String state, Boolean initial) {
        this(machineId, parentState, state, initial, null, null, null);
    }

    /**
     * Instantiates a new jpa repository state.
     *
     * @param machineId    the machine id
     * @param parentState  the parent state
     * @param state        the state
     * @param initial      the initial
     * @param stateActions the state actions
     * @param entryActions the entry actions
     * @param exitActions  the exit actions
     */
    public FsmState(String machineId, FsmState parentState, String state, Boolean initial, Set<FsmAction> stateActions,
                              Set<FsmAction> entryActions, Set<FsmAction> exitActions) {
        this.machineId = machineId == null ? "" : machineId;
        this.parentState = parentState;
        this.state = state;
        this.initial = initial;
        this.stateActions = stateActions;
        this.entryActions = entryActions;
        this.exitActions = exitActions;
    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    @Override
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public FsmState getParentState() {
        return parentState;
    }

    public void setParentState(FsmState parentState) {
        this.parentState = parentState;
    }

    @Override
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public PseudoStateKind getKind() {
        return kind;
    }

    public void setKind(PseudoStateKind kind) {
        this.kind = kind;
    }

    @Override
    public Boolean isInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }

    @Override
    public RepositoryAction getInitialAction() {
        return initialAction;
    }

    public void setInitialAction(FsmAction initialAction) {
        this.initialAction = initialAction;
    }

    @Override
    public Set<FsmAction> getStateActions() {
        return stateActions;
    }

    public void setStateActions(Set<FsmAction> stateActions) {
        this.stateActions = stateActions;
    }

    @Override
    public Set<FsmAction> getEntryActions() {
        return entryActions;
    }

    public void setEntryActions(Set<FsmAction> entryActions) {
        this.entryActions = entryActions;
    }

    @Override
    public Set<FsmAction> getExitActions() {
        return exitActions;
    }

    public void setExitActions(Set<FsmAction> exitActions) {
        this.exitActions = exitActions;
    }

    @Override
    public Set<String> getDeferredEvents() {
        return deferredEvents;
    }

    public void setDeferredEvents(Set<String> deferredEvents) {
        this.deferredEvents = deferredEvents;
    }

    @Override
    public String getSubmachineId() {
        return submachineId;
    }

    public void setSubmachineId(String submachineId) {
        this.submachineId = submachineId;
    }



    public static FsmState ofInitial(Long id ,String machineId ,String state ,Boolean initial ,PseudoStateKind kind
            ,Set<FsmAction> stateActions ,Set<FsmAction> entryActions ,Set<FsmAction> exitActions
            , FsmDefinition fsmDefinition) {
        return new FsmState(
                id
                , machineId
                , state
                , null
                , initial
                , kind
                , null
                , null
                , null
                , stateActions
                , entryActions
                , exitActions
                , null
                , fsmDefinition
        );
    }

    public Long getId() {
        return id;
    }

    public Long getDefinitionId(){
        return fsmDefinition.getId();
    }
}