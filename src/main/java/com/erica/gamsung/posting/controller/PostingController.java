package com.erica.gamsung.posting.controller;

import com.erica.gamsung.posting.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/api/v1/postings/{reservationId}")
    public PostingDetailResponse getPostingDetail(@PathVariable Long reservationId) {

        return postingService.getDetail(reservationId);
    }

    @GetMapping("/api/v1/postings/{reservationId}/state")
    public PostingStateResponse getPostingState(@PathVariable Long reservationId) {

        return postingService.getState(reservationId);
    }

    @GetMapping("/api/v1/postings/state")
    public List<PostingStateResponse> getPostingStateList() {

        return postingService.getStateList();
    }

    @DeleteMapping("/api/v1/postings/{reservationId}/delete")
    public void deletePosting(@PathVariable Long reservationId) {

        postingService.delete(reservationId);
    }

//    @PostMapping("/api/v1/postings/{reservationId}/option")
//    public PostingOptionRequest PostingOption(@RequestBody PostingOptionRequest requests, @PathVariable Long reservationId) {
//
//        return postingService.postOption(requests);
//    }

    @PostMapping("/api/v1/postings/option")
    public List<PostingOptionRequest> PostingOption(@RequestBody List<PostingOptionRequest> requests) {

        return postingService.postOption(requests);
    }
    @PostMapping("api/v1/postings/post/{postingId}")
    public void postPosting(@RequestBody PostPostingRequest posting, @PathVariable Long postingId){
        postingService.postPosting(posting,postingId);
        return;
    }
}

// 자바 객체 -> JSON (직렬화)
// JSON -> 자바 객체 (역직렬화)