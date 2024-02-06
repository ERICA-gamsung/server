package com.erica.gamsung.storeInfo.controller;

import com.erica.gamsung.storeInfo.service.StoreInfoDetailResponse;
import com.erica.gamsung.storeInfo.service.StoreInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreInfoController {
    private final StoreInfoService storeInfoService;

    @GetMapping("/api/v1/storeInfos/{storeInfoId}")
    public StoreInfoDetailResponse getStoreInfoDetail(@PathVariable Long storeInfoId) {
        return storeInfoService.getDetail(storeInfoId);
    }

    @PatchMapping("/api/v1/storeInfos/{storeInfoId}")
    public StoreInfoDetailResponse updateStoreInfoDetail(@PathVariable Long storeInfoId) {

}
