package com.tiru.local_ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AIService {

    //private final RestClient restClient;
    private final ChatClient chatClient;


    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
        /* SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30_000);
        requestFactory.setReadTimeout(120_000); */

        /* this.restClient = RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl("http://localhost:11434")
                .build(); */
    }

    public String askAI(String question) {
        /* return restClient.post()
                .uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "model", "qwen2.5:3b",
                        "messages", List.of(Map.of(
                                "role", "user",
                                "content", question)),
                        "stream", false))
                .retrieve()
                .body(String.class); */
                return chatClient.prompt().user(question).call().content();
    }
}