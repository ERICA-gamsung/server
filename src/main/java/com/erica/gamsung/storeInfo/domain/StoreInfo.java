package com.erica.gamsung.storeInfo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
//    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    private LocalTime closeTime;
    private String openDay;
    private String address;
    private String phoneNumber;
}
