package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import com.erica.gamsung.storeInfo.repository.StoreInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class StoreInfoService {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    public StoreInfoDetailResponse getDetail(Long storeInfoId) {
        StoreInfo storeInfo = storeInfoRepository.findById(storeInfoId).orElseThrow(() ->
                new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + storeInfoId));

        return StoreInfoDetailResponse.from(storeInfo);
    }

    public CreateStoreInfoRequest createDetail(CreateStoreInfoRequest request) {
        StoreInfo storeInfo = new StoreInfo(
                request.id(),
                request.name(),
                request.type(),
                request.openTime(),
                request.closeTime(),
                request.openDay(),
                request.address(),
                request.phoneNumber()
        );

        storeInfoRepository.save(storeInfo);

        return CreateStoreInfoRequest.from(storeInfo);
    }

    @Transactional
    public UpdateStoreInfoRequest updateDetail(Long id, UpdateStoreInfoRequest request) {
        StoreInfo storeInfo = storeInfoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + id));

        storeInfo.update(request);

        return UpdateStoreInfoRequest.from(storeInfo);
    }

    @PostConstruct
    public void init() {
        if (storeInfoRepository.count() > 0) {
            return;
        }

        // 더미 데이터 작성
//        StoreInfo store1 = new StoreInfo(
//                1L,
//                "감성식당",
//                "음식점",
//                LocalTime.of(9, 0, 0),
//                LocalTime.of(21, 0, 0),
//                "월화수목금",
//                "한양대학 1길",
//                "02-1234-5678"
//        );
//
//        StoreInfo store2 = new StoreInfo(
//                2L,
//                "현기식당",
//                "음식점",
//                LocalTime.of(11, 0, 0),
//                LocalTime.of(22, 0, 0),
//                "월화목금",
//                "한양대학 2길",
//                "02-3253-5678"
//        );
//
//        StoreInfo store3 = new StoreInfo(
//                3L,
//                "종윤식당",
//                "카페",
//                LocalTime.of(7, 0, 0),
//                LocalTime.of(23, 0, 0),
//                "월화수목금",
//                "한양대학 3길",
//                "02-3258-5678"
//        );

        // 더미 데이터 저장
//        storeInfoRepository.saveAll(List.of(store1, store2, store3));
    }
}
