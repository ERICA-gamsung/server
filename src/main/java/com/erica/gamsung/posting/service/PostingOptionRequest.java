package com.erica.gamsung.posting.service;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PostingOptionRequest {
    private LocalDate date;
    private LocalTime time;

    private String menu;
    private String event;
    private String message;

    public PostingOptionRequest(LocalDate date, LocalTime time, String menu, String event, String message) {
        this.date = date;
        this.time = time;
        this.menu = menu;
        this.event = event;
        this.message = message;
    }
}
