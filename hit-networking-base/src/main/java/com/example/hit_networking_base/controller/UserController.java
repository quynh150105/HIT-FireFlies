package com.example.hit_networking_base.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/helo")
    @Operation(summary = "Cong khai", security = {})
    public String helo(){
        return "HELLO";
    }
}
