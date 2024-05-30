package com.erica.gamsung.member.controller;

import com.erica.gamsung.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping(path = "/api/v1/member/delete",headers = "Authorization")
    public void deleteMember(@RequestHeader String Authorization){
        String token = Authorization.replace("Bearer ", "").trim();
        memberService.deleteMember(token);
    }
}
