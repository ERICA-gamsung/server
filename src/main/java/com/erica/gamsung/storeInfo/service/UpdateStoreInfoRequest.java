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
        String openTime,
        String closeTime,
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

//@Getter
//@NoArgsConstructor
//public class UpdateStoreInfoRequest extends StoreInfo {
//    private Long id;
//    private String name;
//    private String type;
//    private LocalTime openTime;
//    private LocalTime closeTime;
//    private String openDay;
//    private String address;
//    private String phoneNumber;
//
//    public UpdateStoreInfoRequest(Long id, String name, String type, LocalTime openTime, LocalTime closeTime, String openDay, String address, String phoneNumber) {
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
