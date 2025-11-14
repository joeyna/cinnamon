package com.joeyworks.cinnamon.alarm.repository;

import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmDefinitionRepository extends JpaRepository<AlarmDefinition, Long> {
    Optional<AlarmDefinition> findByEventKey(String eventKey);
}
