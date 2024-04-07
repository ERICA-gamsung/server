package com.erica.gamsung.posting.service;

import com.erica.gamsung.posting.utils.ImageUrListConverter;
import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.Convert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DeletePosting {
    private Long reservationId;

    private LocalDate date;
    private LocalTime time;

    private String menu;
    private String event;
    private String message;

    @Convert(converter = StringListConverter.class)
    private List<String> contents;
    private String fixedContent;

    @Convert(converter = ImageUrListConverter.class)
    private List<String> imageUrl;

    private String state;

    public DeletePosting(Long reservationId, LocalDate date, LocalTime time, String menu, String event, String message, List<String> contents, String fixedContent, List<String> imageUrl, String state) {
        this.reservationId = reservationId;
        this.date = date;
        this.time = time;
        this.menu = menu;
        this.event = event;
        this.message = message;
        this.contents = contents;
        this.fixedContent = fixedContent;
        this.imageUrl = imageUrl;
        this.state = state;
    }
}
