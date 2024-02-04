package com.erica.gamsung.posting.repository;

import com.erica.gamsung.posting.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {
}
