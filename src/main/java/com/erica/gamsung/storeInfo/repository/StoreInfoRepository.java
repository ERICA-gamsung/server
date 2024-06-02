package com.erica.gamsung.storeInfo.repository;

import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.storeInfo.domain.StoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> {
    StoreInfo findByToken(String token);
}
