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

    private String getPrompt(Posting posting) {
        String menu = posting.getMenu();
        String event = posting.getEvent();
        String message = posting.getMessage();

        String prompt = String.format("""
                    나는 음식점을 운영하고 있어. 내 음식점을 홍보하기 위한 홍보 문구를 작성해. 홍보 문구는 홍보할 메뉴, 이벤트, 고객에게 전달하고 싶은 메시지 등을 고려해서 3가지 버전으로 작성하는데 "@" 기호를 구분자로 각 버전 사이에 사용하고 줄 바꿈 문자는 사용하면 안돼.
                    홍보할 메뉴 :  %s
                    이벤트 : %s
                    고객에게 전달하고 싶은 메시지 : %s
                    """, menu, event, message);

        return prompt;
    }

    public List<String> getContents(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        String prompt = getPrompt(posting);

        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        String ans = response.getChoices().get(0).getMessage().getContent();

        return stringListConverter.convertToEntityAttribute(ans);
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateContents() {
        List<Posting> postings = postingRepository.findAll();

        for (Posting posting : postings) {

            if (posting.getContents() == null || posting.getContents().isEmpty()) {
                List<String> contents = getContents(posting.getReservationId());

                posting.setContents(contents);

                postingRepository.save(posting);
            }
        }
    }
}
