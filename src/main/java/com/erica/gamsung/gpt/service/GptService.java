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

import java.time.LocalDate;
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

        int cnt = 2;

        String prompt = String.format("""
                    내가 운영하는 음식점을 소개하고 싶어. 내 음식점을 홍보하기 위한 홍보 글를 작성해줘.
                    글 작성 시 고려해야 할 사항 :
                    1. 각 홍보 글은 최소 5문장 이상으로 작성해.
                    """);
        if (menu != null) {
            prompt += String.format("%d. 홍보하고싶은 메뉴는 %s(이)야. %s 맛집으로 홍보를 해.\n", cnt, menu, menu);
        }
        else {
            prompt += String.format("%d. 홍보하고싶은 메뉴는 없으니 간단한 인사나 근황 소개와 함께 한 번 방문해보는 것은 어떤지 작성해.\n", cnt);
        }

        if (event != null) {
            prompt += String.format("%d. %s 이벤트를 한다는 것을 포함해서 작성하고, 고객들이 이 글을 보고 이벤트를 참여할 수 있게 홍보를 해.\n", ++cnt, event);
        }

        if (message != null) {
            prompt += String.format("%d. 고객들에게 \"%s\"라는 말을 전하고 싶어. 작성할 글의 스타일에 맞게 수정해서 추가해.\n", ++cnt, message);
        }

        prompt += String.format("%d. 마지막에는 해시태그를 작성해. #맛집, #메뉴이름, #이벤트이름 등을 포함해서 작성해.\n", ++cnt);
        prompt += String.format("%d. 출력 예시는 꼭 지켜야 해.\n", ++cnt);

        prompt += String.format("""
                출력 예시에 대한 설명으로는 다음과 같아.
                위 %d개의 사항(들)을 모두 고려한 서로 다른 3개의 홍보 글을 작성하고 각 버전의 제일 마지막에 "@"기호를 1개 사용해서 출력해.
                예를 들어 작성한 3개의 버전이 각각 "버전 1 : 안녕 #맛집", "버전 2 : 안녕하세요. #먹스타그램", "버전 3 : 안녕하십니까! #초밥"일 때의 출력예시를 알려줄게.
                각 버전의 "버전 1 : ", "버전 2 : ", "버전 3 : "을 빼고 다음의 출력 예시처럼 출력해.
                출력 예시 :
                안녕 #맛집@
                안녕하세요. #먹스타그램@
                안녕하십니까! #초밥
                """, cnt);

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
