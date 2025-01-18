package org.baekya_be.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Slf4j
@RestController
public class ChatController {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    public List<String> data = List.of(
            "data1",
            "data2",
            "data3",
            "data4"
    );

    @GetMapping("/search")
    public List<String> searchChat() {
        return data;
    }

    @GetMapping("/chatbot")
    public ResponseEntity<Map<String, Object>> initChatSession() {

        String welcomeMessage1 = "안녕하세요!";
        String welcomeMessage2 = "직무와 관련된 경험을 저에게 공유해주세요";

        List<String> messages = new ArrayList<>();
        messages.add(welcomeMessage1);
        messages.add(welcomeMessage2);

        Map<String, Object> response = new HashMap<>();
        response.put("role", "assistant");
        response.put("content", messages);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/ai")
    public ResponseEntity<?> handleChatRequest(@RequestBody(required = false) String content) {
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Content cannot be empty.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-3.5-turbo");
        requestPayload.put("messages", Collections.singletonList(
                Map.of("role", "user", "content", content)
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            log.info("Sending request to OpenAI API...");
            log.info("User content: " + content);

            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);

            log.info("Response Status: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Error communicating with OpenAI API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request.");
        }
    }



}


