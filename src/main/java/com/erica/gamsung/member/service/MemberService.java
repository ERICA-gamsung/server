package com.erica.gamsung.member.service;

import com.erica.gamsung.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public void postToken(PostTokenRequest postTokenRequest){
        //new member인지 기존 멤버인지 확인
        //refreshToken 발급 필요
    }
}
