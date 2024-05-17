package com.erica.gamsung.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provider;
    private String providerId;
    private String storeName;
    private String role;
    @Column(length = 1024)
    private String accessToken;
    @Builder
    public Member(String provider,String providerId,String role,String accessToken) {
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.accessToken = accessToken;
    }
}
