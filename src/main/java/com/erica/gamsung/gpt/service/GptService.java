package com.erica.gamsung.gpt.service;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import com.erica.gamsung.posting.utils.StringListConverter;

import com.erica.gamsung.gpt.dto.GptRequest;
import com.erica.gamsung.gpt.dto.GptResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GptService {
    @Autowired
    private PostingRepository postingRepository;

    private final StringListConverter stringListConverter = new StringListConverter();

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private String getPrompt(Posting posting) {
        String menu = posting.getMenu();
        String event = posting.getEvent();
        String message = posting.getMessage();

        String prompt = String.format("""
                    나는 음식점을 운영하고 있어. 내 음식점을 홍보하기 위한 홍보 문구를 작성해. 홍보 문구는 홍보할 메뉴, 이벤트, 고객에게 전달하고 싶은 메시지가 있으면 고려해서 3가지  작성해. 각 버전 사이에는 "@" 기호를 구분자로 사용하고 줄 바꿈 문자는 사용하면 안돼.
                    """);
        if (menu != null) {
            prompt += String.format("홍보할 메뉴 : %s\n", menu);
        }
        if (event != null) {
            prompt += String.format("이벤트 : %s\n", event);
        }
        if (message != null) {
            prompt += String.format("고객에게 전달하고 싶은 메시지 : %s\n", message);
        }

        return prompt;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    @Async("threadPoolTaskExecutor")
    public void getContents(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        String prompt = getPrompt(posting);

        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        List<String> contents = stringListConverter.convertToEntityAttribute(
                response.getChoices().get(0).getMessage().getContent()
        );

        posting.setContents(contents);
    }
}
