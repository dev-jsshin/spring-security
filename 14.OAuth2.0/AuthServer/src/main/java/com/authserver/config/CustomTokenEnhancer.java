package com.authserver.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.ZoneId;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    /*
     * 현재 토큰을 받고 추가 세부 정보가 포함된 향상된 토큰을 반환한다. enhance()
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
                                     OAuth2Authentication oAuth2Authentication) {

        // 수신한 토큰을 바탕으로 새 토큰 생성
        var token = new DefaultOAuth2AccessToken(oAuth2AccessToken);

        // 토큰에 추가할 세부 정보를 Map 정의
        Map<String, Object> info = Map.of("generatedInZone", ZoneId.systemDefault().toString());
        token.setAdditionalInformation(info);

        return token;
    }
}