package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//@Component
@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepository;

    public PostingDetailResponse getDetail(Long postingId) {
        Posting posting = postingRepository.findById(postingId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + postingId));

        return new PostingDetailResponse(posting.getId(), posting.getImageUrl(), posting.getFixedContent(), posting.getReservedAt(), posting.getContents());
//        return new PostingDetailResponse(1L, "", "1", LocalDateTime.now(), List.of("안녕하세요", "밥 먹어", "잘가"));
    }

    @PostConstruct
    public void init() {
        // 데이터가 이미 존재하면 바로 리턴
        if (postingRepository.count() > 0) {
            return;
        }

        // 더미 데이터 작성
        Posting posting1 = new Posting(
                1L,
                -1L,
                -1L,
                "http://example.s3.com/image1.png",
                null,
                LocalDateTime.of(2024, 1, 24, 17, 0, 0),
                List.of("우리 가게로 놀러오세요!", "오늘 요리 맛있습니다!", "너만 오면 고!")
        );

        Posting posting2 = new Posting(
                2L,
                -1L,
                -1L,
                "http://example.s3.com/image2.png",
                "오늘은 1000원 할인",
                LocalDateTime.of(2024, 2, 24, 21, 0, 0),
                List.of("오늘은 1000원 할인", "제철 고등어 드세요", "너만 오면 고!")
        );

        Posting posting3 = new Posting(
                3L,
                -1L,
                -1L,
                null,
                "와 정말 맛있다!",
                LocalDateTime.of(2024, 3, 2, 9, 0, 0),
                List.of("와 정말 맛있다!", "오늘 요리 맛있습니다!", "잘먹었습니다!")
        );

        // 더미 데이터 저장
        postingRepository.saveAll(List.of(posting1, posting2, posting3));
    }
}
