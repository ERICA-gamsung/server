package com.erica.gamsung.storeInfo.service;

import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import com.erica.gamsung.storeInfo.domain.StoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreInfoService {

    @Autowired
    private MemberRepository memberRepository;

    public StoreInfoDetailResponse getDetail(Long memberId) {
        StoreInfo storeInfo = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + memberId)).getStoreInfo();

        return StoreInfoDetailResponse.from(storeInfo);
    }

    @Transactional
    public UpdateStoreInfoRequest updateDetail(Long memberId, UpdateStoreInfoRequest request) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("StoreInfo가 존재하지 않습니다. storeInfoId: " + memberId));
        if (member.getStoreInfo() == null) {
            StoreInfo storeInfo = new StoreInfo(request.name(), request.type(), request.openTime(), request.closeTime(), request.openDay(), request.address(), request.phoneNumber());
            member.setStoreInfo(storeInfo);
        } else {
            member.getStoreInfo().update(request);
        }

        return UpdateStoreInfoRequest.from(member.getStoreInfo());
    }

}
