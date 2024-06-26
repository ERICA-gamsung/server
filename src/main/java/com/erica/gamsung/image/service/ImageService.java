package com.erica.gamsung.image.service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class ImageService {
    private final PostingRepository postingRepository;
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
    private String bucketName = "gamsung-bucket";
    public List<String> uploadImage(PostImageRequest postImageRequest,Long reservationId){
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        Posting post = postingRepository.findById(reservationId).get();
        int count = 1;
        for(MultipartFile file : postImageRequest.files()){
            String type = Objects.requireNonNull(file.getContentType());
            switch (type){
                case "image/jpeg":
                    type = ".jpeg";
                    break;
                case "image/png":
                    type=".png";
                    break;
                default:
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,"jpeg 파일만 가능합니다.");
            }
//            final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_NORTHEAST_2).build();
            final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
            try {
                String fileName = "";
                fileName = reservationId+"_"+count+type;
                count++;
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(),metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                String imageUrl = amazonS3.getUrl(bucketName, fileName).toString();
                post.setImageUrl(imageUrl);
                postingRepository.save(post);
            }
            catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return List.of(postingRepository.findById(reservationId).get().getImageUrl());
    }
}