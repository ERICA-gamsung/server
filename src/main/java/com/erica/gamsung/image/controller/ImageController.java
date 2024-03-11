package com.erica.gamsung.image.controller;
import com.erica.gamsung.image.service.ImageService;
import com.erica.gamsung.image.service.PostImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/image")
public class ImageController {
    final private ImageService imageService;
    @PostMapping(path = "post/{postingId}")
    public List<String> postImage(PostImageRequest postImageRequest, @PathVariable Long postingId) {
        return imageService.uploadImage(postImageRequest,postingId);
    }
}