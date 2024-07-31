package com.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)  // 로그인 페이지 비활성화
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
