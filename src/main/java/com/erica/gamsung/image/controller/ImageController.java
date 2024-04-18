package com.erica.gamsung.image.controller;
import com.erica.gamsung.image.service.ImageService;
import com.erica.gamsung.image.service.PostImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/image")
public class ImageController {
    final private ImageService imageService;
    @PostMapping(path = "post/{reservationId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> postImage(@ModelAttribute PostImageRequest postImageRequest, @PathVariable Long reservationId) {
        return imageService.uploadImage(postImageRequest,reservationId);
    }
}