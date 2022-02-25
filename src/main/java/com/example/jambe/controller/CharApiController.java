package com.example.jambe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class CharApiController {

    @GetMapping("/api/v1/chat")
    public String chat(Principal principal) {

        String username = principal.getName();

        log.info("==================================");
        log.info("@ChatController, GET Chat / Username : " + username);

        return "chat";
    }
}
