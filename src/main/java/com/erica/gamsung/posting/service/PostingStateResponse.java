package com.erica.gamsung.posting.service;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PostingStateResponse {
    private Long reservationId;

    private LocalDate date;
    private LocalTime time;

    private String state;

    public PostingStateResponse(Long reservationId, LocalDate date, LocalTime time, String state) {
        this.reservationId = reservationId;
        this.date = date;
        this.time = time;
        this.state = state;
    }
}
