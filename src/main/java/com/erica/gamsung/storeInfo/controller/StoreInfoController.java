package com.erica.gamsung.storeInfo.controller;

import com.erica.gamsung.storeInfo.service.CreateStoreInfoRequest;
import com.erica.gamsung.storeInfo.service.StoreInfoDetailResponse;
import com.erica.gamsung.storeInfo.service.StoreInfoService;
import com.erica.gamsung.storeInfo.service.UpdateStoreInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
public class StoreInfoController {
    private final StoreInfoService storeInfoService;

    @GetMapping("/api/v1/storeInfos/{memberId}")
    public StoreInfoDetailResponse getStoreInfoDetail(@PathVariable Long memberId) {

        return storeInfoService.getDetail(memberId);
    }

    @PutMapping("/api/v1/storeInfos/{memberId}")
    public UpdateStoreInfoRequest updateStoreInfoDetail(
            @PathVariable Long memberId,
            @RequestBody UpdateStoreInfoRequest request) {
        return storeInfoService.updateDetail(memberId, request);
    }

}
