package com.joeyworks.cinnamon.synology.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeyworks.cinnamon.synology.dto.SynologyAlarmWebhookDto;
import com.joeyworks.cinnamon.synology.service.SynologyAlarmWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sysnology/alarm/webhook")
public class SynologyAlarmWebhookController {

    private final ObjectMapper objectMapper;
    private final SynologyAlarmWebhookService synologyAlarmService;

    @PostMapping("/")
    public ResponseEntity<Void> createAlarm(@RequestBody SynologyAlarmWebhookDto dto) throws Exception {
        log.info(dto.toString());

        synologyAlarmService.createAlarm(dto);

        return ResponseEntity.created(null).build();
    }
}