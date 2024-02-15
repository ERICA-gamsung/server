package com.erica.gamsung.storeInfo.domain;

import com.erica.gamsung.storeInfo.service.UpdateStoreInfoRequest;
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
//    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
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
