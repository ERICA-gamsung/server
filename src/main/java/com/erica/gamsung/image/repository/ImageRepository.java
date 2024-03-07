package com.erica.gamsung.image.repository;

import com.erica.gamsung.image.domain.Image;
import com.erica.gamsung.posting.domain.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    void deleteByPostingId(Long postingId);
}