package com.businessserver.security.config;


import com.businessserver.security.filter.InitialAuthenticationFilter;
import com.businessserver.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final InitialAuthenticationFilter initialAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(InitialAuthenticationFilter initialAuthenticationFilter, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.initialAuthenticationFilter = initialAuthenticationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(c -> {c.disable();});

        http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
            .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated());

        return http.build();
    }
}