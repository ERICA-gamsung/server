package com.erica.gamsung.posting.service;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PostingOptionRequest {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private LocalDate date;
    private LocalTime time;

    private String menu;
    private String event;
    private String message;

    public PostingOptionRequest(Long reservationId, LocalDate date, LocalTime time, String menu, String event, String message) {
        this.reservationId = reservationId;
        this.date = date;
        this.time = time;
        this.menu = menu;
        this.event = event;
        this.message = message;
    }
}
