package com.erica.gamsung.posting.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private Long reservationId;

    private LocalDate date;
    private LocalTime time;

    private String imageUrl;
    private String fixedContent;

    private List<String> contents;

    public PostingDetailResponse(Long reservationId, LocalDate date, LocalTime time, String imageUrl, String fixedContent, List<String> contents) {
        this.reservationId = reservationId;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
        this.fixedContent = fixedContent;
        this.contents = contents;
    }
}
