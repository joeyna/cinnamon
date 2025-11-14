package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.statemachine.data.RepositoryGuard;

@Entity
@Table(name = "fsm_guard")
public class FsmGuard extends RepositoryGuard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "spel")
    private String spel;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSpel() {
        return spel;
    }

    public void setSpel(String spel) {
        this.spel = spel;
    }
}
