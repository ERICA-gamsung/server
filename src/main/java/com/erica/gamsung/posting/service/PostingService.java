package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//@Component
@Service
public class PostingService {

    @Autowired
    private PostingRepository postingRepository;

    public PostingDetailResponse getDetail(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        return new PostingDetailResponse(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getImageUrl(), posting.getFixedContent(), posting.getContents());
    }

    public PostingStateResponse getState(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        return new PostingStateResponse(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getState());
    }

    public List<PostingStateResponse> getStateList() {
        List<Posting> postings = postingRepository.findAll();
        List<PostingStateResponse> responseList = new ArrayList<>();

        for (Posting posting : postings) {
            responseList.add(new PostingStateResponse(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getState()));
        }

        return responseList;
    }

    public DeletePosting delete(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        postingRepository.delete(posting);

        return new DeletePosting(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getMenu(), posting.getEvent(), posting.getMessage(), posting.getContents(), posting.getFixedContent(), posting.getImageUrl(), posting.getState());
    }

    public List<PostingOptionRequest> postOption(List<PostingOptionRequest> requests) {
        List<PostingOptionRequest> requestList = new ArrayList<>();

        for (PostingOptionRequest request : requests) {
            Posting posting = new Posting(
                    1L,
                    request.getReservationId(),
                    request.getDate(),
                    request.getTime(),
                    request.getMenu(),
                    request.getEvent(),
                    request.getMessage(),
                    List.of("", "", ""),
                    "",
                    List.of(""),
                    "yet"
            );

            postingRepository.save(posting);

            requestList.add(new PostingOptionRequest(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getMenu(), posting.getEvent(), posting.getMessage()));
        }

        return requestList;
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
                1L,
                LocalDate.of(2024, 1, 24),
                LocalTime.of(17, 0),
                "김치찌개",
                null,
                null,
                List.of("우리 가게로 놀러오세요!", "오늘 요리 맛있습니다!", "너만 오면 고!"),
                "안녕하세요! 오늘은 김치찌개가 준비되어 있습니다. 많이 오세요!",
                List.of("http://example.s3.com/image1.png"),
                "ready"
        );

        Posting posting2 = new Posting(
                1L,
                2L,
                LocalDate.of(2024, 3, 4),
                LocalTime.of(9, 0),
                "돈코츠 라멘",
                "4명당 음료수 1개 무료",
                "새학기 힘내세요!",
                List.of("오늘은 1000원 할인", "라멘 드세요", "너만 오면 고!"),
                "안녕하세요! 오늘은 돈코츠 라멘이 준비되어 있습니다. 많이 오세요!",
                List.of("http://example.s3.com/image2.png"),
                "yet"
        );

        Posting posting3 = new Posting(
                1L,
                3L,
                LocalDate.of(2024, 4, 2),
                LocalTime.of(12, 30),
                "제육볶음",
                null,
                "신메뉴 시식해보세요",
                List.of("와 정말 맛있다!", "오늘 요리 맛있습니다!", "잘먹었습니다!"),
                "안녕하세요! 오늘은 제육볶음이 준비되어 있습니다. 많이 오세요!",
                List.of("http://example.s3.com/image3.png"),
                "done"
        );

        // 더미 데이터 저장
        postingRepository.saveAll(List.of(posting1, posting2, posting3));
    }
}
