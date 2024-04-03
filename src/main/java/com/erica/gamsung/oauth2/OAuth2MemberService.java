package com.erica.gamsung.oauth2;

import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
//    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(userRequest.getClientRegistration().getRegistrationId());
//        try {
            Long providerId = Long.parseLong((String) oAuth2User.getAttributes().get("id"));
            oAuth2User.getAttributes().get("id");
            String provider = "facebook";
            String role = "ROLE_USER";
            System.out.println(oAuth2User.getAttributes());
            Optional<Member> findMember = memberRepository.findByProviderId(providerId);
            Member member = null;
            if (findMember.isEmpty()) { //찾지 못했다면
                member = Member.builder()
                        .provider(provider)
                        .role(role)
                        .providerId(providerId).build();
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
