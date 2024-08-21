package com.prepostauthorize.controller;

import com.prepostauthorize.model.Document;
import com.prepostauthorize.service.PreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PreController {

    @Autowired
    private PreService preService;

    @GetMapping("/pre1")
    public String pre1() {
        return "Hello, " + preService.getName();
    }

    @GetMapping("/pre2/{name}")
    public List<String> pre2(@PathVariable String name) {
        return preService.getSecretNames(name);
    }

    @GetMapping("/pre3/{code}")
    public Document pre3(@PathVariable String code) {
        return preService.getDocument(code);
    }
}
