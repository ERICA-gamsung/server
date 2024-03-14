package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

@Builder(access = AccessLevel.PRIVATE)
public record UpdateStoreInfoRequest(
        Long id,
        String name,
        String type,

        LocalTime openTime,
        LocalTime closeTime,

        String openDay,
        String address,
        String phoneNumber
) {
    public static UpdateStoreInfoRequest from(StoreInfo storeInfo) {
        return UpdateStoreInfoRequest.builder()
                .id(storeInfo.getId())
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
