package com.erica.gamsung.storeInfo.domain;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private String openTime;
    private String closeTime;
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
