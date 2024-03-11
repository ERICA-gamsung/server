package com.erica.gamsung.posting.domain;
import com.erica.gamsung.posting.utils.ImageUrListConverter;
import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservationId;
    private Long userId;
    @Convert(converter = ImageUrListConverter.class)
    private List<String> imageUrl;
    private String fixedContent;
    private LocalDateTime reservedAt;
    @Convert(converter = StringListConverter.class)
    private List<String> contents;
}