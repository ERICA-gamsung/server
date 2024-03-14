package com.erica.gamsung.storeInfo.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class CreateStoreInfoRequest {
    private String name;
    private String type;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    private String openDay;
    private String address;
    private String phoneNumber;
}
