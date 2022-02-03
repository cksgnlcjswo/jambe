package com.example.jambe.controller;

import com.example.jambe.dto.MemberDto;
import com.example.jambe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/user/signup")
    public String execSignUp(MemberDto memberDto) {
        memberService.joinUser(memberDto);
        return "redirect:/user/login";
    }
}
