package com.example.jambe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping("/")
    public String main_page() {
        System.out.println("index page");
        return "index";
    }
}
