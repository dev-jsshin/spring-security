package com.cors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     * Controller에서 @CrossOrigin 으로 엔드포인트 별로 지정할 수 있지만
     * 중앙화해서 한곳에서 관리하는 전략이 좋아보임
     * 아래는 SecurityConfig 에서 정의했지만 class를 분리해야할 것으로 생각됨 (가독성 등)
     *
     * setAllowedOrigins : 허용되는 출처 도메인
     * setAllowedMethods : 허용할 HTTP Method
     */
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("POST"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:8080"));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(corsConfigurer -> corsConfigurer
                .configurationSource(corsConfigurationSource()));

        /*
         * CORS에 집중하기 위해 CSRF 비활성화
         */
        http.csrf(c -> c.disable())
            .authorizeRequests(auth -> auth
                    .anyRequest().permitAll());

        return http.build();
    }
}
