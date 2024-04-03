package com.erica.gamsung.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long providerId;
    private String storeName;
    private String role;
    @Builder
    public Member(String provider, Long providerId,String role) {
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }
}
