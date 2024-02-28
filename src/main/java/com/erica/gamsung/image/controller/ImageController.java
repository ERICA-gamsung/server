package com.erica.gamsung.image.controller;
import com.erica.gamsung.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/image")
public class ImageController {
    final private ImageService imageService;
    @PostMapping(path = "post/{postingId}")
    public String postImage(@RequestBody MultipartFile file, @PathVariable String postingId) {
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            return "파일 형식이 잘못됨";
        }
        imageService.uploadImage(file);
        return "success";
    }
}