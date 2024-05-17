package com.erica.gamsung.oauth2.controller;

import com.erica.gamsung.oauth2.dto.GetOAuth2Response;
import com.erica.gamsung.oauth2.service.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuth2Controller {
    private final OAuth2Service oAuth2Service;
    @GetMapping(path = "get/{uuid}")
    public ResponseEntity<GetOAuth2Response> getToken(@PathVariable("uuid") String uuid){
        return oAuth2Service.getToken(uuid);
    }
}
