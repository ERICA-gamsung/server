package com.erica.gamsung.storeInfo.controller;

import com.erica.gamsung.storeInfo.service.StoreInfoDetailResponse;
import com.erica.gamsung.storeInfo.service.StoreInfoService;
import com.erica.gamsung.storeInfo.service.UpdateStoreInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreInfoController {
    private final StoreInfoService storeInfoService;

    @GetMapping("/api/v1/storeInfos/{storeInfoId}")
    public StoreInfoDetailResponse getStoreInfoDetail(@PathVariable Long storeInfoId) {
        return storeInfoService.getDetail(storeInfoId);
    }

    @PutMapping("/api/v1/storeInfos/{storeInfoId}")
    public UpdateStoreInfoRequest updateStoreInfoDetail(@PathVariable Long storeInfoId) {
        return storeInfoService.updateDetail(storeInfoId);
    }

}
