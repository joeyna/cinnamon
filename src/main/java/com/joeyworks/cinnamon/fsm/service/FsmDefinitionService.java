package com.joeyworks.cinnamon.fsm.service;

import com.joeyworks.cinnamon.fsm.dto.FsmDefinitionDto;
import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import com.joeyworks.cinnamon.fsm.repository.FsmDefinitionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FsmDefinitionService {

    private final FsmDefinitionRepository fsmDefinitionRepository;

    public FsmDefinitionDto getDefinition(Long id) {
        FsmDefinition fsmDefinition = fsmDefinitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("fsm_definition not found"));
        return FsmDefinitionDto.from(fsmDefinition);
    }

    /**
     * 생성시 최신버전의 definition을 적용.
     * @param definitionKey
     * @return
     */
    public FsmDefinition getTheLatestDefinitionByDefinitionKey(String definitionKey) {
        return fsmDefinitionRepository.findTop1ByDefinitionKeyOrderByVersionDesc(definitionKey)
                .orElseThrow(() -> new EntityNotFoundException("fsm_definition not found by definitionKey : " + definitionKey));
    }

    public Long saveDefinition(FsmDefinitionDto fsmDefinitionDto) {
        FsmDefinition saved = fsmDefinitionRepository.save(fsmDefinitionDto.toEntity());

        return saved.getId();
    }

}
