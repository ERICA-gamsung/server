package com.erica.gamsung.posting.domain;

import com.erica.gamsung.posting.utils.ImageUrListConverter;
import com.erica.gamsung.posting.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
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

    @Convert(converter = ImageUrListConverter.class)
    private List<String> imageUrl;

    /*
     * yet : 글 3개가 발행조차 되지 않은 상태
     * not_fix : 글 3개 중 1개 , 이미지 확정되지 않은 상태
     * ready : 인스타그램에 올라갈 수 있는 상태
     * done : 실제로 인스타그램에 올라간 상태
     */

    private String state; // yet, not_fix, ready, done

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
