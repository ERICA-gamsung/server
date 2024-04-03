package com.erica.gamsung.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;


public class InstagramMemberInfo {
    private Map<String, Object> attributes;

    public InstagramMemberInfo(Map<String, Object> attributes) {
        attributes.get("id");
    }

    public Long getProviderId() {
        return (Long) attributes.get("id");
    }
}
