package com.erica.gamsung.gpt.service;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import com.erica.gamsung.posting.utils.StringListConverter;

import com.erica.gamsung.gpt.dto.GptRequest;
import com.erica.gamsung.gpt.dto.GptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GptService {
    @Autowired
    private PostingRepository postingRepository;

    private final StringListConverter stringListConverter = new StringListConverter();

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getContents(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        String prompt = posting.getPrompt();

        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        String ans = response.getChoices().get(0).getMessage().getContent();

        return stringListConverter.convertToEntityAttribute(ans);
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateContents() {
        List<Posting> postings = postingRepository.findAll();

        for (Posting posting : postings) {
            String prompt = posting.getPrompt();

            if (prompt != null && !prompt.isEmpty() && (posting.getContents() == null || posting.getContents().isEmpty())) {
                List<String> contents = getContents(posting.getReservationId());

                posting.setContents(contents);

                postingRepository.save(posting);
            }
        }
    }
}