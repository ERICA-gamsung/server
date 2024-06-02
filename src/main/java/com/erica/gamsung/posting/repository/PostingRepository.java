package com.erica.gamsung.posting.repository;

import com.erica.gamsung.posting.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findAllByMemberId(Long memberId);
}
