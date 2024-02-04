package com.erica.gamsung.posting.domain;

import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservationId;
    private Long userId;
    private String imageUrl;
    private String fixedContent;
    private LocalDateTime reservedAt;
    @Convert(converter = StringListConverter.class)
    private List<String> contents;
}
