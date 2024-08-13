package com.resourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /*
     * 인증 서버를 CALL하는 check_token 방식도 있지만 이 어풀리케이션은 리소스 서버가 DB I/F를 통해 토큰 검사
     * check_token 방식은 사용자가 리소스를 요청할 때 마다 인증 서버에 토큰 유무 검사를 하기 때문에
     * 리소스 서버 Client/Secret 도 적용해야한다.
     * 리소스 서버 yaml 파일에 check_token 설정 정의 가능
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
