package com.erica.gamsung.storeInfo.controller;

import com.erica.gamsung.storeInfo.service.StoreInfoDetailResponse;
import com.erica.gamsung.storeInfo.service.StoreInfoService;
import com.erica.gamsung.storeInfo.service.UpdateStoreInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StoreInfoController {
    private final StoreInfoService storeInfoService;

    @GetMapping("/api/v1/storeInfos/{storeInfoId}")
    public StoreInfoDetailResponse getStoreInfoDetail(@PathVariable Long storeInfoId) {
        return storeInfoService.getDetail(storeInfoId);
    }

//    @PutMapping("/api/v1/storeInfos/{storeInfoId}")
//    public UpdateStoreInfoRequest updateStoreInfoDetail(
//            @PathVariable Long storeInfoId
//            @RequestBody UpdateStoreInfoRequest request) {
//        return storeInfoService.updateDetail(storeInfoId, request);
//    }

}
