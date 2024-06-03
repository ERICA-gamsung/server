package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateStoreInfoRequest(
        String name,
        String type,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime openTime,
        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime closeTime,

        String openDay,
        String address,
        String phoneNumber
) {
    public static UpdateStoreInfoRequest from(StoreInfo storeInfo) {
        return UpdateStoreInfoRequest.builder()
                .name(storeInfo.getName())
                .type(storeInfo.getType())
                .openTime(storeInfo.getOpenTime())
                .closeTime(storeInfo.getCloseTime())
                .openDay(storeInfo.getOpenDay())
                .address(storeInfo.getAddress())
                .phoneNumber(storeInfo.getPhoneNumber())
                .build();
    }
}
