package com.endpointauthorization.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /*
     * POST
     * http://localhost:8080/a
     * john:12345 (base64)
     * ADMIN ROLE 만 가능 (john)
     */
    @PostMapping("/a")
    public String postEndpointA() {
        return "post - a";
    }

    /*
     * GET
     * http://localhost:8080/a
     * 누구나 가능
     */
    @GetMapping("/a")
    public String getEndpointA() {
        return "get - a";
    }

    /*
     * GET
     * http://localhost:8080/a/b
     * john,jane:12345 (base64)
     * ADMIN, MANAGER ROLE 만 가능 (john,jane)
     */
    @GetMapping("/a/b")
    public String getEnpointB() {
        return "Works!";
    }

    /*
     * GET
     * http://localhost:8080/a/b/c
     * john,jane:12345 (base64)
     * ADMIN, MANAGER ROLE 만 가능 (john,jane)
     */
    @GetMapping("/a/b/c")
    public String getEnpointC() {
        return "Works!";
    }
}
