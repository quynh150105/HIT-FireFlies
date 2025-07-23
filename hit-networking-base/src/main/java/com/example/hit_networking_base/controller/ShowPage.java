package com.example.hit_networking_base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowPage {

    @GetMapping("/auth/set-front")
    public String showPage(){
        return "resetPassword";
    }
}
