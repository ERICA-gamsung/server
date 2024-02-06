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
    private String openDay;
    private String address;
    private String phoneNumber;

    public UpdateStoreInfoRequest(Long id, String name, String type, LocalTime openTime, LocalTime closeTime, String openDay, String address, String phoneNumber) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setOpenTime(openTime);
        this.setCloseTime(closeTime);
        this.setOpenDay(openDay);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
    }
}
