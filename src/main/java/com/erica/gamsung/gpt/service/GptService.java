package com.erica.gamsung.gpt.service;
import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
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
    @Autowired
    private MemberRepository memberRepository;

    private final StringListConverter stringListConverter = new StringListConverter();

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private String getPrompt(String storeName, Posting posting) {
        String menu = posting.getMenu();
        String event = posting.getEvent();
        String message = posting.getMessage();

        int cnt = 2;

        String prompt = String.format("""
                    나는 "%s"라는 음식점을 운영하고 있어. 이 음식점을 인스타그램에 홍보하고 싶어. 사람들의 시선을 끌 수 있는 글을 작성해줘. 이모티콘 써도 좋아.
                    글 작성 시 고려해야 할 사항 :
                    1. 각 홍보 글은 최소 3문장 정도로 작성해.
                    """, storeName);
        if (menu != null) {
            prompt += String.format("%d. 홍보하고 싶은 메뉴는 %s(이)야. %s 맛집으로 홍보를 해.\n", cnt, menu, menu);
        }
        else {
            prompt += String.format("%d. 홍보하고 싶은 메뉴는 없으니 간단한 인사나 근황 소개와 함께 한 번 방문해보는 것은 어떤지 작성해.\n", cnt);
        }

        if (event != null) {
            prompt += String.format("%d. %s 이벤트를 한다는 것을 포함해서 작성하고, 고객들이 이 글을 보고 이벤트를 참여할 수 있게 홍보를 해.\n", ++cnt, event);
        }

        if (message != null) {
            prompt += String.format("%d. 고객들에게 \"%s\"라는 말을 전하고 싶어. 작성할 글의 스타일에 맞게 수정해서 추가해.\n", ++cnt, message);
        }

        prompt += String.format("%d. 마지막에는 해시태그를 작성해.\n", ++cnt);
        prompt += String.format("%d. 출력 예시는 꼭 지켜야 해.\n", ++cnt);

        prompt += String.format("""
                출력 예시:
                    위 %d개의 사항(들)을 모두 고려한 홍보 글 3개를 작성해줘.
                    글 구분은 마지막에 @ 붙여서 해줘. 글 앞에 글 구분하려고 뭔가 붙이지 마. 절대 아무것도 붙이지 마.
                    각 글에서 한 줄이 끝날 때마다 \n 붙여서 줄바꿈 표시해줘.
                """, cnt);

        return prompt;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    @Async("threadPoolTaskExecutor")
    public void getContents(String token, Long reservationId) {
        Member member = memberRepository.findByAccessToken(token)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 member id입니다."));

        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        String storeName = member.getStoreInfo().getName();

        String prompt = getPrompt(storeName, posting);

        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        List<String> contents = stringListConverter.convertToEntityAttribute(
                response.getChoices().get(0).getMessage().getContent()
        );

        posting.setContents(contents);
    }
}