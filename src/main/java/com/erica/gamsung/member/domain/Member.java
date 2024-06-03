package com.erica.gamsung.member.domain;

import com.erica.gamsung.menu.domain.Menu;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.storeInfo.domain.StoreInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<Posting> postings;

    @Embedded
    private StoreInfo storeInfo;
//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Menu> menu;
    @Builder
    public Member(String provider,String providerId,String role,String accessToken, StoreInfo storeInfo) {
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.accessToken = accessToken;
        this.storeInfo = storeInfo;
    }
}
