package com.erica.gamsung.posting.controller;

import com.erica.gamsung.posting.service.PostingDetailResponse;
import com.erica.gamsung.posting.service.PostingService;
import com.erica.gamsung.posting.service.PostingStateResponse;
import com.erica.gamsung.posting.service.PostingOptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor    // final 붙은 멤버들에 대해서 생성자를 롬복이 만들어준다.
public class PostingController {
//    1. @Autowired를 통한 의존성 주입
//    @Autowired
//    private PostingService postingService;

    private final PostingService postingService;

    // Controller -> Service를 의존한다.

//    public PostingController(PostingService postingService) {
//        this.postingService = postingService;
//    }

    @GetMapping("/api/v1/postings/{postingId}")
    public PostingDetailResponse getPostingDetail(@PathVariable Long postingId) {
        return postingService.getDetail(postingId);
//        return new PostingDetailResponse(
//                posting_id,
//                "http://example.s3.com/image1.jpg",
//                null,
//                LocalDateTime.now(),
//                List.of("안녕하세요", "우리 가게로 놀러오세요", "반갑습니다")
//        );
    }

    @GetMapping("/api/v1/postings/{postingId}/state")
    public PostingStateResponse getPostingState(@PathVariable Long postingId) {
        return postingService.getState(postingId);
    }

    @DeleteMapping("/api/v1/postings/{postingId}/delete")
    public void deletePosting(@PathVariable Long postingId) {
        postingService.delete(postingId);
    }

    @PostMapping("/api/v1/postings/{postingId}/option")
    public PostingOptionRequest PostingOption(@RequestBody PostingOptionRequest request, @PathVariable String postingId) {

        return PostingService.postOption(request);
    }

}

// 자바 객체 -> JSON (직렬화)
// JSON -> 자바 객체 (역직렬화)