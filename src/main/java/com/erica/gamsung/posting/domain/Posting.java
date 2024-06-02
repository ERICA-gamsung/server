package com.erica.gamsung.posting.domain;
import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.posting.utils.ImageUrListConverter;
import com.erica.gamsung.posting.utils.StringListConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Posting {
    @ManyToOne
    private Member member;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private String menu;
    private String event;
    private String message;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "varchar(1000)")
    private List<String> contents;
    @Column(columnDefinition = "varchar(1000)")
    private String fixedContent;

    private String imageUrl;

    public Posting(Member member, LocalDate date, LocalTime time, String menu, String event, String message,
                   List<String> contents, String fixedContent, List<String> imageUrl, String state) {
        this.member = member;
        this.date = date;
        this.time = time;
        this.menu = menu;
        this.event = event;
        this.message = message;
        this.contents = contents;
        this.fixedContent = fixedContent;
        this.imageUrl = imageUrl.get(0);
        this.state = state;
    }

    /*
     * yet : 글 3개가 발행조차 되지 않은 상태
     * not_fix : 글 3개 중 1개 , 이미지 확정되지 않은 상태
     * ready : 인스타그램에 올라갈 수 있는 상태
     * done : 실제로 인스타그램에 올라간 상태
     */
    private String state;
}