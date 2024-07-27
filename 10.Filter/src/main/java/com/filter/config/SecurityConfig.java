package com.filter.config;

import com.filter.filter.AuthenticationLoggingFilter;
import com.filter.filter.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new RequestValidationFilter(),
                             BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthenticationLoggingFilter(),
                             BasicAuthenticationFilter.class)
            .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());
        return http.build();
    }
}
