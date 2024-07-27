package com.filter.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /*
     * http://localhost:8080/hello
     * Request-Id:keeyyyyy (Header)
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
}
