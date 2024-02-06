package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

/*
 * "name": "감성식당",
 * "type": "음식점",
 * "openTime": "09:00",
 * "closeTime": "21:00",
 * "openDay": "월화수목금",
 * "address": "한양대학 1길",
 * "phoneNumber": "02-1234-5678"
 */

@Builder(access = AccessLevel.PRIVATE)
public record StoreInfoDetailResponse(
        Long id,
        String name,
        String type,
        LocalTime openTime,
        LocalTime closeTime,
        String openDay,
        String address,
        String phoneNumber
) {
    public static StoreInfoDetailResponse from(StoreInfo storeInfo) {
        return StoreInfoDetailResponse.builder()
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

//@Getter
//public class StoreInfoDetailResponse {
//    private Long id;
//    private String name;
//    private String type;
//    private String openTime;
//    private String closeTime;
//    private String openDay;
//    private String address;
//    private String phoneNumber;
//
//    public StoreInfoDetailResponse(Long id, String name, String type, String openTime, String closeTime, String openDay, String address, String phoneNumber) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.openTime = openTime;
//        this.closeTime = closeTime;
//        this.openDay = openDay;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//    }
//}
