package com.erica.gamsung.storeInfo.domain;

import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.storeInfo.service.CreateStoreInfoRequest;
import com.erica.gamsung.storeInfo.service.UpdateStoreInfoRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreInfo {
    @OneToOne
    private Member member;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type; // 식당, 카페

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    private String openDay;
    private String address;
    private String phoneNumber;

    public void update(UpdateStoreInfoRequest request) {
        this.name = request.name();
        this.type = request.type();
        this.openTime = request.openTime();
        this.closeTime = request.closeTime();
        this.openDay = request.openDay();
        this.address = request.address();
        this.phoneNumber = request.phoneNumber();
    }
}
