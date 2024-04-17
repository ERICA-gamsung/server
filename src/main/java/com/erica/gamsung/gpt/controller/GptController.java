package com.erica.gamsung.gpt.controller;

import com.erica.gamsung.gpt.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gpt")
public class GptController {
    @Autowired
    private GptService gptService;

    @GetMapping("/get/{reservationId}/content")
    public String getContents(@PathVariable Long reservationId) {
        return gptService.getContents(reservationId).toString();
    }
}