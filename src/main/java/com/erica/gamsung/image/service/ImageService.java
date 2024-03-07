package com.erica.gamsung.image.service;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.erica.gamsung.image.domain.Image;
import com.erica.gamsung.image.repository.ImageRepository;
import com.erica.gamsung.posting.domain.Posting;
import com.erica.gamsung.posting.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostingRepository postingRepository;

    public void uploadImage(PostImageRequest postImageRequest,Long posingId){
        imageRepository.deleteByPostingId(posingId);
        Posting post = postingRepository.findById(posingId).get();
        post.setImageUrl(new ArrayList<>());
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
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,"jpg,png 파일만 가능합니다.");
            }
            final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
            String bucketName = "gamsung-bucket";
            try {
                String fileName = "";
                Image image = new Image();
                fileName = imageRepository.save(image).getId()+type;
//                Image image;
//                if(post.getImageUrl()==null){
//                    image = new Image();
//                    fileName = imageRepository.save(image).getId()+type;
//                }
//                else{
//                    image = imageRepository.findByUrl(post.getImageUrl());
//                    fileName = image.getId()+type;
//                }
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(),metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                image.setUrl(amazonS3.getUrl(bucketName,fileName).toString());
                image.setPosting(post);
                List<String> listUrl = new ArrayList<>(post.getImageUrl());
                listUrl.add(image.getUrl());
                post.setImageUrl(listUrl);
                postingRepository.save(post);
//                List<String> listUrl = post.getImageUrl();
//                post.setImageUrl(listUrl);
//                postingRepository.save(post);
                imageRepository.save(image);
            }
            catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
//        String type = Objects.requireNonNull(postImageRequest.file().getContentType());
//        switch (type){
//            case "image/jpeg":
//                type = ".jpeg";
//                break;
//            case "image/png":
//                type=".png";
//                break;
//            default:
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"jpg,png 파일만 가능합니다.");
//
//        }
//        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
//        String bucketName = "gamsung-bucket";
//        try {
//            Posting post = postingRepository.findById(posingId).get();
//            String fileName = "";
//            Image image;
//            if(post.getImageUrl()==null){
//                image = new Image();
//                fileName = imageRepository.save(image).getId()+type;
//            }
//            else{
//                image = imageRepository.findByUrl(post.getImageUrl());
//                fileName = image.getId()+type;
//            }
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(postImageRequest.file().getSize());
//            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, postImageRequest.file().getInputStream(),metadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//            image.setUrl(amazonS3.getUrl(bucketName,fileName).toString());
//            post.setImageUrl(image.getUrl());
//            postingRepository.save(post);
//            imageRepository.save(image);
//        }
//        catch (AmazonS3Exception e) {
//            System.err.println(e.getErrorMessage());
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}