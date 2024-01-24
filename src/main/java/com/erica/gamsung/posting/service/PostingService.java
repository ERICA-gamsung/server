package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.controller.PostingDetailResponse;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
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

//        return new PostingDetailResponse(posting.getId(), posting.getImageUrl(), posting.getFixedContent(), posting.getReservedAt(), posting.getContents());
        return new PostingDetailResponse(1L, "", "1", LocalDateTime.now(), List.of("안녕하세요", "밥 먹어", "잘가"));
    }
}
