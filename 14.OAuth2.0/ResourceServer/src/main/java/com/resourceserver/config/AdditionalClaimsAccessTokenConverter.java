package com.resourceserver.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

public class AdditionalClaimsAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        var authentication = super.extractAuthentication(map); // JwtAccessTokenConverter 클래스가 구현하는 논리를 적용해 초기 Authentication 객체를 얻음
        authentication.setDetails(map); // Authentication 객체에 맞춤형 세부 정보 추가
        return authentication;
    }
}