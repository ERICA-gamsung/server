package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

@Builder(access = AccessLevel.PRIVATE)
public record CreateStoreInfoRequest(
        Long id,
        String name,
        String type,
        String openTime,
        String closeTime,
        String openDay,
        String address,
        String phoneNumber
) {

//    public static CreateStoreInfoRequest from(Long id, String name, String type, LocalTime openTime, LocalTime closeTime, String openDay, String address, String phoneNumber) {
//        return CreateStoreInfoRequest.builder()
//                .id(id)
//                .name(name)
//                .type(type)
//                .openTime(openTime)
//                .closeTime(closeTime)
//                .openDay(openDay)
//                .address(address)
//                .phoneNumber(phoneNumber)
//                .build();
//    }

    public static CreateStoreInfoRequest from(StoreInfo storeInfo) {
        return CreateStoreInfoRequest.builder()
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
