package com.erica.gamsung.oauth2.repository;

import com.erica.gamsung.oauth2.domain.OAuth2Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2MemoRepository extends JpaRepository<OAuth2Memo,Long> {
}
