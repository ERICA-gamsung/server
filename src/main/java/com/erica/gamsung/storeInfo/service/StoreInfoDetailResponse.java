package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

/*
 * "id": 1,
 * "name": "감성식당",
 * "type": "식당",
 * "openTime": "09:00",
 * "closeTime": "21:00",
 * "openDay": "월화수목금",
 * "address": "한양대학 1길",
 * "phoneNumber": "02-1234-5678"
 */

@Builder(access = AccessLevel.PRIVATE)
public record StoreInfoDetailResponse(
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
    public static StoreInfoDetailResponse from(StoreInfo storeInfo) {
        return StoreInfoDetailResponse.builder()
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
