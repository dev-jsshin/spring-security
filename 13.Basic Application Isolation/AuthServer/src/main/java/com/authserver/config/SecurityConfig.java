package com.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * 본 어플리케이션은 MSA 관점에서 바라보는 인증 서버가 아니다.
 * AuthServer 라고 명시되어 있는 본 어플리케이션의 비즈니스는
 * 사용자 추가 / OTP 인증번호 발급 / 간단한 OTP 인증번호 일치 여부 확인용이다.
 * [Only 어플리케이션 분리에 중점을 둔다]
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 어플리케이션 분리에 중점을 두기 위해 타 어플리케이션과 I/F 시 CSRF 비활성화 및 접근 권한을 모두 허용한다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(c -> {c.disable();});

        http.authorizeRequests().anyRequest().permitAll();

        return http.build();
    }
}
