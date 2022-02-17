package com.example.jambe.controller;

import com.example.jambe.domain.Member.Member;
import com.example.jambe.dto.Member.MemberDto;
import com.example.jambe.dto.Member.MemberResponseDto;
import com.example.jambe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class memberController {

    private final MemberService memberService;

    @GetMapping("/user/signup")
    public String signUpPage() {
        return "signUp";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/user/signup")
    public ResponseEntity<MemberResponseDto> execSignUp(@RequestBody MemberDto memberDto) {

        Member member = memberService.joinUser(memberDto);

        return new ResponseEntity(Member.convertToResponseDto(member), HttpStatus.CREATED);
    }
}
