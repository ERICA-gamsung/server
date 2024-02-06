package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import com.erica.gamsung.storeInfo.repository.StoreInfoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.List;

@Service
public class StoreInfoService {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    public StoreInfoDetailResponse getDetail(Long storeInfoId) {
        StoreInfo storeInfo = storeInfoRepository.findById(storeInfoId).orElseThrow(() ->
                new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + storeInfoId));

        return new StoreInfoDetailResponse(storeInfo.getId(), storeInfo.getName(), storeInfo.getType(), storeInfo.getOpenTime(), storeInfo.getCloseTime(), storeInfo.getOpenDay(), storeInfo.getAddress(), storeInfo.getPhoneNumber());
    }

    @Transactional
    public UpdateStoreInfoRequest updateDetail(
            @PathVariable Long storeInfoId,
            @RequestBody UpdateStoreInfoRequest request) {
        StoreInfo storeInfo = storeInfoRepository.findById(storeInfoId).orElseThrow(() ->
            new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + storeInfoId));

        storeInfo.setId(request.getId());
        storeInfo.setName(request.getName());
        storeInfo.setType(request.getType());
        storeInfo.setOpenTime(request.getOpenTime());
        storeInfo.setCloseTime(request.getCloseTime());
        storeInfo.setOpenDay(request.getOpenDay());
        storeInfo.setAddress(request.getAddress());
        storeInfo.setPhoneNumber(request.getPhoneNumber());

        return null;
    }

    @PostConstruct
    public void init() {
        if (storeInfoRepository.count() > 0) {
            return;
        }

        // 더미 데이터 작성
        StoreInfo store1 = new StoreInfo(
                1L,
                "감성식당",
                "음식점",
                LocalTime.of(9, 0, 0),
                LocalTime.of(21, 0, 0),
                "월화수목금",
                "한양대학 1길",
                "02-1234-5678"
        );

        StoreInfo store2 = new StoreInfo(
                2L,
                "현기식당",
                "음식점",
                LocalTime.of(11, 0, 0),
                LocalTime.of(22, 0, 0),
                "월화목금",
                "한양대학 2길",
                "02-3253-5678"
        );

        StoreInfo store3 = new StoreInfo(
                3L,
                "종윤식당",
                "카페",
                LocalTime.of(7, 0, 0),
                LocalTime.of(23, 0, 0),
                "월화수목금",
                "한양대학 3길",
                "02-3258-5678"
        );

        // 더미 데이터 저장
        storeInfoRepository.saveAll(List.of(store1, store2, store3));
    }
}
