package org.baekya_be.Controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.baekya_be.DTO.AIContentDTO;
import org.baekya_be.DTO.SeniorExpDTO;
import org.baekya_be.Service.SeniorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import org.baekya_be.Domain.Senior;


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
    public ResponseEntity<Object> initChat() {

        String welcomeMessage1 = "안녕하세요!";
        String welcomeMessage2 = "직무와 관련된 경험을 저에게 공유해주세요";

        List<Map<String, String>> response = new ArrayList<>();

        Map<String, String> message1 = new HashMap<>();
        message1.put("role", "assistant");
        message1.put("content", welcomeMessage1);

        Map<String, String> message2 = new HashMap<>();
        message2.put("role", "assistant");
        message2.put("content", welcomeMessage2);

        response.add(message1);
        response.add(message2);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }


    @PostMapping("/ai")
    public ResponseEntity<?> handleChatRequest(@RequestBody(required = false)AIContentDTO dto) {
        System.out.println("api_key : " + API_KEY);
        String content = dto.getContent();
        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Content cannot be empty.");
        }

        String content2;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("model", "gpt-4o");
        requestPayload.put("messages", Collections.singletonList(
                Map.of("role", "user", "content", content)
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
        RestTemplate restTemplate = new RestTemplate();


        content2 = request + "이것을 요약해서 요약한 것만을 출력해줘";

        Map<String, Object> requestPayload2 = new HashMap<>();
        requestPayload2.put("model", "gpt-4o");
        requestPayload2.put("messages", Collections.singletonList(
                Map.of("role", "user", "content", content2)
        ));
        HttpEntity<Map<String, Object>> request2 = new HttpEntity<>(requestPayload2, headers);
        RestTemplate restTemplate2 = new RestTemplate();


        try {
            log.info("Sending request to OpenAI API...");
            log.info("User content: " + content);
            log.info("User content: " + content2);

            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);
            log.info("Response Status: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());

            ResponseEntity<String> response2 = restTemplate2.postForEntity(OPENAI_API_URL, request2, String.class);
            // 응답 내용 추출
            String responseContent = response2.getBody();
            String responseContent1 = response.getBody();

            // JSON 파싱을 위한 ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseContent);
            JsonNode jsonNode1 = objectMapper.readTree(responseContent1);

            // "choices" 배열 안에서 첫 번째 항목의 "text" 필드 추출
            String gptAnswer = jsonNode.get("choices").get(0).get("message").get("content").asText();
            SeniorExpDTO exp_dto = new SeniorExpDTO(dto.getUser_id(),gptAnswer);
            SeniorService service = new SeniorService();
            service.updateSeniorExperience(exp_dto);

            String gptAnswer1 = jsonNode1.get("choices").get(0).get("message").get("content").asText();


            log.info("Response2 Status: " + response2.getStatusCode());
            log.info("Response2 Body: " + response2.getBody());

            return ResponseEntity.ok(gptAnswer1);


        } catch (Exception e) {
            log.error("Error communicating with OpenAI API", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request.");
        }
    }



}


