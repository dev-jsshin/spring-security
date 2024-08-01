package com.csrf.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @RequestMapping(value = "/main")
    public String main(Authentication a, Model model) {
        model.addAttribute("username",a.getName());
        return "main";
    }

    @PostMapping("/hello")
    @ResponseBody
    public String postHello() {
        return "Post Hello!";
    }

}