package com.erica.gamsung.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record PostImageRequest (List<MultipartFile> files){

}
