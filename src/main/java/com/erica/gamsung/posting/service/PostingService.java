package com.erica.gamsung.posting.service;

import com.erica.gamsung.gpt.service.GptService;
import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class PostingService {
    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GptService gptService;
    private final PostingUploadService postingUploadService;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public PostingDetailResponse getDetail(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        return new PostingDetailResponse(posting.getReservationId(), posting.getDate(), posting.getTime(), posting.getContents(), posting.getFixedContent(), List.of(posting.getImageUrl()));
    }

    public List<StateListResponse> getStateList(Long id) {
        List<Posting> postings = postingRepository.findAllByMemberId(id);
        List<StateListResponse> StateListResponses = postings.stream().map(StateListResponse::new).collect(Collectors.toList());

        return StateListResponses;
    }

    public void delete(Long reservationId) {
        Posting posting = postingRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("Posting이 존재하지 않습니다. postingId: " + reservationId));

        postingRepository.delete(posting);
    }

    public List<PostingOptionRequest> postOption(String token,List<PostingOptionRequest> requests) {
        List<PostingOptionRequest> requestList = new ArrayList<>();
        Member member = memberRepository.findByAccessToken(token)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 member id입니다."));
        List<Posting> postingList = new ArrayList<>();
        for (PostingOptionRequest request : requests) {
            Posting posting = new Posting(
                    member,
                    request.getDate(),
                    request.getTime(),
                    request.getMenu(),
                    request.getEvent(),
                    request.getMessage(),
                    List.of(""),
                    "",
                    List.of(""),
                    "yet"
            );

            postingRepository.save(posting);
            postingList.add(posting);
            gptService.getContents(token, posting.getReservationId());
            requestList.add(new PostingOptionRequest(posting.getDate(),posting.getTime(), posting.getMenu(), posting.getEvent(), posting.getMessage()));
        }
        member.setPostings(postingList);
        memberRepository.save(member);
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
//            postingUploadService.postingUpload(post);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime targetTime = LocalDateTime.of(post.getDate(),post.getTime());
            long delay = ChronoUnit.MINUTES.between(now,targetTime);
            log.info("now : "+now + " target : "+targetTime + " delay : "+delay);
            executorService.schedule(()->{
                postingUploadService.postingUpload(post);
                post.setState("done");
                postingRepository.save(post);
            },delay, TimeUnit.MINUTES);
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
                null,
                1L,
                LocalDate.of(2024, 1, 24),
                LocalTime.of(17, 0),
                "김치찌개",
                null,
                null,
                List.of("우리 가게로 놀러오세요!", "오늘 요리 맛있습니다!", "너만 오면 고!"),
                "안녕하세요! 오늘은 김치찌개가 준비되어 있습니다. 많이 오세요!",
                "http://example.s3.com/image1.png",
                "ready"
        );

        Posting posting2 = new Posting(
                null,
                2L,
                LocalDate.of(2024, 3, 4),
                LocalTime.of(9, 0),
                "돈코츠 라멘",
                "4명당 음료수 1개 무료",
                "새학기 힘내세요!",
                List.of("", "", ""),
                "",
                "http://example.s3.com/image2.png",
                "yet"
        );

        Posting posting3 = new Posting(
                null,
                3L,
                LocalDate.of(2024, 4, 2),
                LocalTime.of(12, 30),
                "제육볶음",
                null,
                "신메뉴 시식해보세요",
                List.of("와 정말 맛있다!", "오늘 요리 맛있습니다!", "잘먹었습니다!"),
                "안녕하세요! 오늘은 제육볶음이 준비되어 있습니다. 많이 오세요!",
                "http://example.s3.com/image3.png",
                "done"
        );

        // 더미 데이터 저장
        postingRepository.saveAll(List.of(posting1, posting2, posting3));
    }
}
