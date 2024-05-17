package com.erica.gamsung.oauth2.dto;

import com.erica.gamsung.member.domain.Member;

public record GetOAuth2Response (String providerId, String accessToken){
    public GetOAuth2Response(Member member){
        this(member.getProviderId(),member.getAccessToken());
    }
}