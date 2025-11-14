package com.joeyworks.cinnamon.slack.service;

import com.joeyworks.cinnamon.slack.dto.SlackNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackNotificationService{

    @Value("${slack.channel}")
    private String channel;

    private final WebClient slackWebClient;

    public void sendMessage(String message){
        //현재는 고정 채널 발송.
        SlackNotificationDto dto = SlackNotificationDto.builder()
                .channel(channel)
                .text(message)
                .build();

        sendMessage(dto);
    }

    private void sendMessage(SlackNotificationDto dto){
        slackWebClient.post()
                .uri("/chat.postMessage")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();
    }
}
