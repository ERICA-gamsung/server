package com.erica.gamsung.image.service;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.erica.gamsung.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public void uploadImage(MultipartFile file){
//        file.getName();
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
        String bucketName = "gamsung-bucket";
        try {;
            amazonS3.putObject(new PutObjectRequest(bucketName, file.getOriginalFilename(), file.getInputStream(), new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }
       catch (IOException e) {
            throw new RuntimeException(e);
        }
//        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
//        if (s3.doesBucketExistV2(bucketName)) {
//            System.out.format("Bucket %s already exists.\n", bucketName);
//            b = getBucket(bucketName);
//        } else {
//            try {
//                b = s3.createBucket(bucket_name);
//            } catch (AmazonS3Exception e) {
//                System.err.println(e.getErrorMessage());
//            }
//        }
//        return b;
    }
}