package com.joeyworks.cinnamon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SlackWebClientConfig {


    @Value("${slack.token}")
    private String slackToken;

    @Bean
    public WebClient slackWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://slack.com/api")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", slackToken)
                .build();
    }
}
