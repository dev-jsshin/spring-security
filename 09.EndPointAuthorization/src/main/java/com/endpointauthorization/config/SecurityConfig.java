package com.endpointauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*
     * WebSecurityConfigureAdapter Deprecated 이슈
     * configure 방식에서 SecurityFilterChain 방식 사용
     * mvcMatchers(), antMatchers(), regexMatchers() -> requestMatchers() 방식 사용
     * mvcMatchers(), antMatchers() 비슷하나 보안적인 측면에서 mvcMatchers() 사용 권장
     * mvcMatchers()는 어플리케이션 내 구현되어있는 엔드포인트를 직접 Mapping 하는 구조
     * antMatchers()는 명시된 규칙을 어플리케이션에 입히는 방식
     *
     * ex) /hello 라는 엔드포인트를 mvcMatchers(), antMatchers() 메소드를 사용하여 규칙을 적용했다면
     * mvcMatchers() 메소드는 /hello/ 도 적용되지만 antMatchers()는 /hello 만 적용됨
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.GET, "/a").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/a").permitAll()  // permitAll() 모든 요청에 대해서 허용하지만 Header에 사용자 정보를 넣으면 인증 필터가 가로채서 인증 프로세스를 수행함
                                                                               // 스프링 시큐리티 동작 순서상 Header에 인증 정보를 넣으면 권한 부여 필터에 도달하기 전 인증 필터에서 먼저 걸러짐
                                                                               // Header에 인증 정보를 안넣으면 인증 필터는 패스함
                        .requestMatchers(HttpMethod.GET, "/a/b/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        /* Spring Security version 6.1 이후 적용
         * 본 어플리케이션은 학습을 위해 임시로 csrf disabled 처리한다.
         */
        http.csrf((csrfConfig)->
                csrfConfig.disable()
        );

        return http.build();
    }
}