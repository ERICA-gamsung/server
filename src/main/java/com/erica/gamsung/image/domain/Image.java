package com.erica.gamsung.image.domain;

import com.erica.gamsung.posting.domain.Posting;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    private Long id;
    private String path;
    @OneToOne()
    @JoinColumn()
    private Posting posting;
}