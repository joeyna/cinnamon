package com.joeyworks.cinnamon.fsm.controller;

import com.joeyworks.cinnamon.fsm.dto.FsmDefinitionDto;
import com.joeyworks.cinnamon.fsm.service.FsmDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fsm-definition")
public class FsmDefinitionController {
    private final FsmDefinitionService fsmDefinitionService;

    @GetMapping("/{id}")
    public ResponseEntity<FsmDefinitionDto> getDefinition(@PathVariable long id) {
        FsmDefinitionDto dto = fsmDefinitionService.getDefinition(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/")
    public ResponseEntity<Void> createDefinition(@RequestBody FsmDefinitionDto dto) {
        Long id = fsmDefinitionService.saveDefinition(dto);
        URI location = URI.create("/fsm-definition/" + id);
        return ResponseEntity.created(location).build();  // HTTP 200 OK + Body: id
    }
}
