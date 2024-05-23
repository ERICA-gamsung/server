package com.erica.gamsung.posting.service;

import com.erica.gamsung.member.domain.Member;
import com.erica.gamsung.posting.domain.Posting;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class PostingUploadService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    public void postingUpload(Posting posting){
        Member member = posting.getMember();
        String token = member.getAccessToken();
        Long pageId = getPageId(token);
        Long instagramId = getInstagramId(pageId,token);
        Long itemContainerId = setItemContainerId(posting.getImageUrl().get(0),posting.getFixedContent(),instagramId,token);
        postingSingleUpload(instagramId,itemContainerId,token);
//        if(posting.getImageUrl().size()>1){
//            String imageContainerIdList = "";
//            for(String imageUrl:posting.getImageUrl()){
//                Long imageContainerId = setImageContainer(imageUrl,instagramId,token);
//                if(imageContainerIdList.isEmpty()){
//                    imageContainerIdList = imageContainerIdList.concat(imageContainerId.toString());
//                }
//                else
//                    imageContainerIdList = imageContainerIdList.concat(","+imageContainerId.toString());
//            }
//            uploadPosting(posting.getFixedContent(),instagramId,imageContainerIdList,token);
//        }
    }
    public Long getPageId(String token) {
        String url = "/me/accounts?access_token="+token;
        Object object = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        JsonNode pageId = objectMapper.valueToTree(object).path("data").get(0).path("id");
        return pageId.asLong();
    }
    public Long getInstagramId(Long pageId,String token){
        String url = "/"+pageId + "?fields=instagram_business_account&access_token="+
                token;
        Object object = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        JsonNode instagramId = objectMapper.valueToTree(object).path("instagram_business_account").path("id");
        return  instagramId.asLong();
    }
    public Long setItemContainerId(String imageUrl,String content,Long instagramId, String token){
        Object object = webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/"+instagramId+"/media")
                        .queryParam("image_url",imageUrl)
                        .queryParam("caption",content)
                        .queryParam("access_token",token)
                        .build()
                )
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        JsonNode containerId = objectMapper.valueToTree(object).path("id");
        return containerId.asLong();
    }
    public Object postingSingleUpload(Long instagramId, Long itemId, String token){
            return webClient
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/"+instagramId+"/media_publish")
                            .queryParam("creation_id",itemId)
                            .queryParam("access_token",token)
                            .build()
                    )
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
    }
//    public Object uploadPosting(String content,Long instagramId,String imageContainerIdList,String token){
//            return webClient
//                    .post()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/"+instagramId+"/media")
//                            .queryParam("caption",content)
//                            .queryParam("media_type","CAROUSEL")
//                            .queryParam("children",imageContainerIdList)
//                            .queryParam("access_token",token)
//                            .build()
//                    )
//                    .retrieve()
//                    .bodyToMono(Object.class)
//                    .block();
//    }
}