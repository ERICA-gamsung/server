package com.erica.gamsung.member.controller;

import com.erica.gamsung.member.service.MemberService;
import com.erica.gamsung.member.service.PostTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/member")
public class MemberController{
    final private MemberService memberService;

    @PostMapping(path = "token")
    public void PostToken(@RequestBody PostTokenRequest postTokenRequest){
        memberService.postToken(postTokenRequest);
        //System.out.println(postTokenRequest.token());
    }
}
