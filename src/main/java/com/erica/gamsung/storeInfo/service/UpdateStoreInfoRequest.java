package com.erica.gamsung.storeInfo.service;

import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateStoreInfoRequest {
    private Long id;
    private String name;
    private String type;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String day;
    private String address;
    private String phone;

    public UpdateStoreInfoRequest(Long id, String name, String type, LocalTime openTime, LocalTime closeTime, String day, String address, String phone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.day = day;
        this.address = address;
        this.phone = phone;
    }
}
