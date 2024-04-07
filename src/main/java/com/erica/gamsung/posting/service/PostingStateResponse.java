package com.erica.gamsung.posting.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostingStateResponse {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reservedAt;
    private String state;

    public PostingStateResponse(Long id, LocalDateTime reservedAt, String state) {
        this.id = id;
        this.reservedAt = reservedAt;
        this.state = state;
    }
}
