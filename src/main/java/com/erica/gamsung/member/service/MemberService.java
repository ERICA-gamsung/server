package com.erica.gamsung.member.service;

import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void deleteMember(String token){
        Member member = memberRepository.findByAccessToken(token)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 member id입니다."));
        memberRepository.delete(member);
    }
}
