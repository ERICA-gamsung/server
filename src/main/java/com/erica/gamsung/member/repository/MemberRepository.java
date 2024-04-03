package com.erica.gamsung.member.repository;

import com.erica.gamsung.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>{
    public Optional<Member> findByProviderId(Long providerId);
}