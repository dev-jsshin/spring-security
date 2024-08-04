package com.businessserver.security.proxy;

import com.businessserver.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
 * 인증서버로 Redirect할 수 있도록 구성된 Proxy
 * 아래 로직은 추후 Spring Cloud (MSA) 학습 시 트랜디하게 구성할 수 있을 것으로 예상
 *
 */
@Component
public class AuthenticationServerProxy {

    @Autowired
    private RestTemplate rest;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(String username, String password) {
        String url = baseUrl + "/user/auth";

        var body = new User();
        body.setUsername(username);
        body.setPassword(password);

        var request = new HttpEntity<>(body);

        rest.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username, String code) {
        String url = baseUrl + "/otp/check";

        var body = new User();
        body.setUsername(username);
        body.setCode(code);

        var request = new HttpEntity<>(body);

        var response = rest.postForEntity(url, request, Void.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }
}