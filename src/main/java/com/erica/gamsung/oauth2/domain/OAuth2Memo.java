package com.erica.gamsung.oauth2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class OAuth2Memo {
    @Id
    private String clientId;
    private String uuid;
    @Builder
    OAuth2Memo(String clientId,String uuid){
        this.clientId = clientId;
        this.uuid = uuid;
    }
}