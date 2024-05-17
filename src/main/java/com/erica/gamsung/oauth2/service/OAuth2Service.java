package com.erica.gamsung.oauth2.service;
import com.erica.gamsung.global.config.redis.RedisUtils;
import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import com.erica.gamsung.oauth2.dto.GetOAuth2Response;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class OAuth2Service {
    private final MemberRepository memberRepository;
    private final RedisUtils redisUtils;
    public ResponseEntity<GetOAuth2Response> getToken(String uuid){
        String providerId = redisUtils.getData(uuid);
        Member member = memberRepository.findByProviderId(providerId).orElseThrow(() ->
                new IllegalArgumentException("관련된 로그인 정보가 없습니다."));;
        return ResponseEntity.ok(new GetOAuth2Response(member));
    }
}