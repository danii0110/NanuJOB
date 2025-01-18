package org.baekya_be.Controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


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


    @PostMapping("/ai")
    public ResponseEntity<?> handleChatRequest(@RequestBody(required = false) Map<String, Object> userRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userRequest, headers);

        try {
            log.info(API_KEY);
            log.info("User Request: " + userRequest);

            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, request, String.class);
            log.info("Response Status: " + response.getStatusCode());
            log.info("Response Body: " + response.getBody());

            // firebase db에 데이터 저장하는 부분을 추가해야 합니다. 만약 오류가 난다면 여기를 수정하세요




            //


            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request.");
        }
    }



}


