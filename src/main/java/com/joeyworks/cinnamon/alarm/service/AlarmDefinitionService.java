package com.joeyworks.cinnamon.alarm.service;

import com.joeyworks.cinnamon.alarm.dto.AlarmDefinitionDto;
import com.joeyworks.cinnamon.alarm.entity.AlarmDefinition;
import com.joeyworks.cinnamon.alarm.repository.AlarmDefinitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmDefinitionService {

    private final AlarmDefinitionRepository alarmDefinitionRepository;

    public AlarmDefinitionDto get(Long id) {
        AlarmDefinition definition = alarmDefinitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("alm_definition not found"));
        return AlarmDefinitionDto.from(definition);
    }

    public AlarmDefinition getByEventKey(String eventKey) {
        return alarmDefinitionRepository.findByEventKey(eventKey)
                .orElseThrow(() -> new EntityNotFoundException("alm_definition not found by event key : " + eventKey));
    }

    public Long save(AlarmDefinitionDto alarmDefinitionDto) {
        AlarmDefinition saved = alarmDefinitionRepository.save(alarmDefinitionDto.toEntity());

        return saved.getId();
    }

}
