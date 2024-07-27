package com.userdetailsmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/*
 * DelegatingPasswordEncoder를 통해 어플리케이션 내 여러 암호화를 사용할 수 있음
 * 접두사로 구분하고 접두사가 없을 시 Default 값으로 구분
 *
 * 두개의 서비스를 하나의 서비스로 통합하는 차세대 프로젝트를 진행한 적이 있는데
 * 서비스 별 사용하는 암호화 방식이 달랐음
 * 당시에는 둘 중 조금 더 Minor한 서비스를 골랐고 패스워드를 초기화하는 전략 (다른 서비스의 암호화 방식으로) 으로 진행했었음...
 * DelegatingPasswordEncoder를 통해 해결할 수 있었던 issue 같음.
 * 로그인은 여러 암호화 방식이 가능하도록, 추후 패스워드 변경시에는 하나의 암호화로
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}
