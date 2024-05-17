package com.erica.gamsung.oauth2.service;

import com.erica.gamsung.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PrincipalDetails implements OAuth2User {
    private Member member;
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collect;
    }
    @Override
    public String getName() {
        return "name";
    }

    public static class InstagramMemberInfo {
        private Map<String, Object> attributes;

        public InstagramMemberInfo(Map<String, Object> attributes) {
            attributes.get("id");
        }

        public Long getProviderId() {
            return (Long) attributes.get("id");
        }
    }
}