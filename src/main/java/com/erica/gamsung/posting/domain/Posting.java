package com.erica.gamsung.posting.domain;
import com.erica.gamsung.posting.utils.ImageUrListConverter;
import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Posting {
    private Long userId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

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
