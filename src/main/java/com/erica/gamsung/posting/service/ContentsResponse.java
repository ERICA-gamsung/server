package com.erica.gamsung.posting.service;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentsResponse {
    private Long reservationId;
    private List<String> contents;

    public ContentsResponse(Long reservationId, List<String> contents) {
        this.reservationId = reservationId;
        this.contents = contents;
    }
}
