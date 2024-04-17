package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        return new DeletePosting(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getMenu(), posting.getEvent(), posting.getMessage(), posting.getPrompt(), posting.getContents(), posting.getFixedContent(), posting.getImageUrl(), posting.getState());
    }

    public List<PostingOptionRequest> postOption(List<PostingOptionRequest> requests) {
        List<PostingOptionRequest> requestList = new ArrayList<>();

        for (PostingOptionRequest request : requests) {
            String menu = request.getMenu();
            String event = request.getEvent();
            String message = request.getMessage();

            String prompt = String.format("""
                    나는 음식점을 운영하고 있어. 내 음식점을 홍보하기 위한 홍보 문구를 작성해. 홍보 문구는 홍보할 메뉴, 이벤트, 고객에게 전달하고 싶은 메시지 등을 고려해서 3가지 버전으로 작성하는데 "@" 기호를 구분자로 각 버전 사이에 사용하고 줄 바꿈 문자는 사용하면 안돼.
                    홍보할 메뉴 :  %s
                    이벤트 : %s
                    고객에게 전달하고 싶은 메시지 : %s
                    """, menu, event, message);

            Posting posting = new Posting(
                    1L,
                    request.getReservationId(),
                    request.getDate(),
                    request.getTime(),
                    menu,
                    event,
                    message,
                    prompt,
                    List.of(""),
                    "",
                    List.of(""),
                    "yet"
            );

            postingRepository.save(posting);

            requestList.add(new PostingOptionRequest(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getMenu(), posting.getEvent(), posting.getMessage()));
        }

        return requestList;
    }
    public void postPosting(PostPostingRequest posting,Long reservationId){
        Optional<Posting> optionalPost = postingRepository.findById(reservationId);
        final Posting post;
        if(optionalPost.isPresent()){
            post = optionalPost.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 ID의 포스트는 존재하지 않습니다.");
        }

        post.setFixedContent(posting.content());
        if(Objects.equals(post.getState(), "ready")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 포스트는 이미 발행 준비 중으로 변경이 불가합니다.");
        }
        else if(!(post.getImageUrl()==null)){
            post.setState("ready");
        }
        else{
            post.setState("not_fix");
        }
        postingRepository.save(post);
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
                "prompt1",
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
                "prompt2",
                List.of("오늘은 1000원 할인", "라멘 드세요", "너만 오면 고!"),
                null,
                List.of("http://example.s3.com/image2.png"),
                "not_fix"
        );

        Posting posting3 = new Posting(
                1L,
                3L,
                LocalDate.of(2024, 4, 2),
                LocalTime.of(12, 30),
                "제육볶음",
                null,
                "신메뉴 시식해보세요",
                "prompt3",
                List.of("와 정말 맛있다!", "오늘 요리 맛있습니다!", "잘먹었습니다!"),
                "안녕하세요! 오늘은 제육볶음이 준비되어 있습니다. 많이 오세요!",
                List.of("http://example.s3.com/image3.png"),
                "done"
        );

        // 더미 데이터 저장
        postingRepository.saveAll(List.of(posting1, posting2, posting3));
    }
}
