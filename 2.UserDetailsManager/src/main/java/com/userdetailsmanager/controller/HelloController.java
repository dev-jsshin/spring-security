package com.userdetailsmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /*
     * http://localhost:8080/hello
     * john:12345 (base64)
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}

