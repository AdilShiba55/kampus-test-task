package com.example.kampustesttask.controller;

import com.example.kampustesttask.util.UtAuthorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {

    @GetMapping("/test")
    public String test() {
        Long userId = UtAuthorization.getUserId();
        String email = UtAuthorization.getUserEmail();
        return "test";
    }

}
