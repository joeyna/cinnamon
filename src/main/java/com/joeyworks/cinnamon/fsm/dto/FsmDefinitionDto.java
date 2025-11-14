package com.joeyworks.cinnamon.fsm.dto;

import com.joeyworks.cinnamon.fsm.entity.FsmDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class FsmDefinitionDto {
    private Long id;
    private String name;
    private Integer version;
    private String description;
    private LocalDateTime createdAt;
    private String definitionKey;

    public static FsmDefinitionDto from(FsmDefinition fsmDefinition){
        return FsmDefinitionDto.builder()
                .id(fsmDefinition.getId())
                .name(fsmDefinition.getName())
                .version(fsmDefinition.getVersion())
                .description(fsmDefinition.getDescription())
                .createdAt(fsmDefinition.getCreatedAt())
                .definitionKey(fsmDefinition.getDefinitionKey())
                .build();
    }

    public FsmDefinition toEntity(){
        return FsmDefinition.of(id, name, version, description, createdAt, definitionKey);
    }
}
