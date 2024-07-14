package com.userdetails.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

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
