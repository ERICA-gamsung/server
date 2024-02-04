package com.erica.gamsung.storeInfo.repository;

import com.erica.gamsung.storeInfo.domain.StoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreInfoRepository extends JpaRepository<StoreInfo, Long> {
}
