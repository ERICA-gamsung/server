package com.erica.gamsung.oauth2.service;

import com.erica.gamsung.global.config.redis.RedisUtils;
import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final RedisUtils redisUtils;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
//        Optional<OAuth2Memo> oAuth2Memo = oauth2MemoRepository.findById(Long.valueOf(userRequest.getClientRegistration().getClientId()));
//        System.out.println(
//                oAuth2Memo.get().getUuid()
//        );
//        try {
            String providerId = (String) oAuth2User.getAttributes().get("id");
            String provider = "facebook";
            String role = "ROLE_USER";
            Optional<Member> findMember = memberRepository.findByProviderId(providerId);
            String uuid = redisUtils.getData(userRequest.getClientRegistration().getClientId());
            redisUtils.setData(uuid,providerId,300000L); //5분
            Member member;
            if (findMember.isEmpty()) { //찾지 못했다면
                member = Member.builder()
                        .provider(provider)
                        .role(role)
                        .providerId(providerId)
                        .accessToken(userRequest.getAccessToken().getTokenValue())
                        .build();
                memberRepository.save(member);
            }
            else{
                member = findMember.get();
            }
            return new PrincipalDetails(member, oAuth2User.getAttributes());
//        } catch(Exception e) {
//            System.out.println("로그인 실패");
//            return null;
//        }
    }
}
//https://localhost:8080/login/oauth2/facebook