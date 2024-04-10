package com.erica.gamsung.gpt.controller;

import com.erica.gamsung.gpt.dto.GptRequest;
import com.erica.gamsung.gpt.dto.GptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gpt")
public class GptController {
    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        return response.getChoices().get(0).getMessage().getContent();
    }
}
