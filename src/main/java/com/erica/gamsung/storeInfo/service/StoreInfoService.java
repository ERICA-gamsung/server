package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import com.erica.gamsung.storeInfo.repository.StoreInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                "09:00",
                "21:00",
                "월화수목금",
                "한양대학 1길",
                "02-1234-5678"
        );

        StoreInfo store2 = new StoreInfo(
                2L,
                "현기식당",
                "음식점",
                "11:00",
                "23:00",
                "월화목금",
                "한양대학 2길",
                "02-3253-5678"
        );

        StoreInfo store3 = new StoreInfo(
                3L,
                "종윤식당",
                "카페",
                "07:00",
                "22:00",
                "월화수목금",
                "한양대학 3길",
                "02-3258-5678"
        );

        // 더미 데이터 저장
        storeInfoRepository.saveAll(List.of(store1, store2, store3));
    }
}
