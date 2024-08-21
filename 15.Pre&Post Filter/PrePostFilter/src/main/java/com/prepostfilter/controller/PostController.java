package com.prepostfilter.controller;

import com.prepostfilter.model.Document;
import com.prepostfilter.model.Employee;
import com.prepostfilter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/post1/{name}")
    public Employee post1(@PathVariable String name) {
        return postService.getBookDetails(name);
    }

    @GetMapping("/post2/{code}")
    public Document post2(@PathVariable String code) {
        return postService.getDocument(code);
    }
}
