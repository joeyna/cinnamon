package com.joeyworks.cinnamon.fsm.repository;

import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FsmDefinitionRepository extends JpaRepository<FsmDefinition, Long> {
    Optional<FsmDefinition> findTop1ByDefinitionKeyOrderByVersionDesc(String definitionKey);
}
