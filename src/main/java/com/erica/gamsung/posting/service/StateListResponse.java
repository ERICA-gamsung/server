package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.domain.Posting;

import java.time.LocalDate;
import java.time.LocalTime;

public record StateListResponse(Long reservationId, LocalDate date, LocalTime time, String state) {
    public StateListResponse(Posting posting) {
        this(posting.getReservationId(),posting.getDate(),posting.getTime(),posting.getState());
    }
}
