package com.erica.gamsung.gpt.controller;

import com.erica.gamsung.gpt.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gpt")
public class GptController {
    @Autowired
    private GptService gptService;

    @GetMapping("/get/{reservationId}/content")
    public List<String> getContents(@PathVariable Long reservationId) {
        return gptService.getContents(reservationId);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt")String prompt) {
        return gptService.chat(prompt);
    }
}
