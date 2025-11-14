package com.joeyworks.cinnamon.slack.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
public class SlackNotificationServiceTest {

    WebClient mockWebClient = Mockito.mock(WebClient .class, Mockito.RETURNS_DEEP_STUBS);
    SlackNotificationService slackNotificationService = new SlackNotificationService(mockWebClient);

    @Test
    void testSendMessage_noException() {
        assertDoesNotThrow(() -> slackNotificationService.sendMessage("text"));

        verify(mockWebClient.post(), times(1));
    }
}
