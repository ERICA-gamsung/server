package com.erica.gamsung.posting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController // @Controller + @ResponseBody
public class PostingController {

    @GetMapping("/api/v1/postings/{posting_id}")
    public PostingDetailResponse getPostingDetail(@PathVariable Long posting_id) {
        return new PostingDetailResponse(
                posting_id,
                "http://example.s3.com/image1.jpg",
                null,
                LocalDateTime.now(),
                List.of("안녕하세요", "우리 가게로 놀러오세요", "반갑습니다")
        );
    }

}

// 자바 객체 -> JSON (직렬화)
// JSON -> 자바 객체 (역직렬화)