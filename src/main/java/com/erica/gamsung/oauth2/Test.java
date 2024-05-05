package com.erica.gamsung.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class Test {
//    @GetMapping(path="/oauth2/facebook")
//    public String test (@RequestParam String access_token) {
//        System.out.println("제발 좀 되라.." + access_token);
//        return "redirect:/";
//    };
}