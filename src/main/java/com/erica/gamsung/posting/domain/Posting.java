package com.erica.gamsung.posting.domain;

import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private LocalDate date;
    private LocalTime time;

    private String menu;
    private String event;
    private String message;

    @Convert(converter = StringListConverter.class)
    private List<String> contents;

    private String fixedContent;

    private String imageUrl;

    private String state; // yet, ready, done
}
