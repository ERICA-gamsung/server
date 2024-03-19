package com.erica.gamsung.posting.service;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class DeletePosting {
    private Long id;
    private String imageUrl;
    private String fixedContent;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime reservedAt;
    private List<String> contents;

    public DeletePosting(Long id, String imageUrl, String fixedContent, LocalDateTime reservedAt, List<String> contents) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.fixedContent = fixedContent;
        this.reservedAt = reservedAt;
        this.contents = contents;
    }
}
