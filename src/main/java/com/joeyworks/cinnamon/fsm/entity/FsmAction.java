package com.joeyworks.cinnamon.fsm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.statemachine.data.RepositoryAction;

@Entity
@Table(name = "fsm_action")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FsmAction extends RepositoryAction {
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


    public static FsmAction of(Long id, String name, String spel){
        return new  FsmAction(id, name, spel);
    }

}
