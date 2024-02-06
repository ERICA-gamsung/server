package com.erica.gamsung.storeInfo.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class UpdateStoreInfoRequest {
    private Long id;
    private String name;
    private String type;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String openDay;
    private String address;
    private String phoneNumber;

    public UpdateStoreInfoRequest(Long id, String name, String type, LocalTime openTime, LocalTime closeTime, String openDay, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openDay = openDay;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
