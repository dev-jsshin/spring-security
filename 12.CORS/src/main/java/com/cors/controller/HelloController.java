package com.cors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class HelloController {

    private Logger logger = Logger.getLogger(HelloController.class.getName());

    @GetMapping("/")
    public String main() {
        return "main.html";
    }

    @PostMapping("/test")
    @ResponseBody
    //@CrossOrigin("http://localhost:8080")
    public String test() {
        logger.info("HELLO");
        return "HELLO";
    }
}