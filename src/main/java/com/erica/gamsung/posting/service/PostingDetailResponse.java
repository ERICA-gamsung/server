package com.erica.gamsung.posting.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/*
 * 	"id": 1,
 * 	"image_url":"",
 * 	"fixed_content" : null,
 * 	"reserved_at": "2024-02-01T09:00"
 * 	"contents" : {
 * 		"우리 가게에 놀러오세요!",
 * 		"오늘 할인해요!",
 * 		"만나서 반갑습니다",
 * 	}
 */

@Getter
public class PostingDetailResponse {
    private Long id;
    private List<String> imageUrl;
    private String fixedContent;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reservedAt;
    private List<String> contents;

    public PostingDetailResponse(Long id, List<String> imageUrl, String fixedContent, LocalDateTime reservedAt, List<String> contents) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.fixedContent = fixedContent;
        this.reservedAt = reservedAt;
        this.contents = contents;
    }
}