package com.roleauthorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    /*
     * InMemory 방식으로 사용자 구성
     * 사용자에게 Role 부여 시 Role 명 앞에 "ROLE_" 접두사는 필수인데 .roles()을 사용하면 접두사가 자동으로 붙음
     * .authorities() 는 접두사 붙여야함 ex)ROLE_ADMIN
     */
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
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> authz.anyRequest().hasRole("ADMIN"))
            .httpBasic(withDefaults());

//        http.authorizeHttpRequests((authz) -> authz.anyRequest().hasAnyRole("ADMIN", "MANAGER"))
//            .httpBasic(withDefaults());

        /*
         * 아래와 같이 23:30 이후에만 접근 허용할 수 있는 특수한 규칙도 적용 가능하다.
         * SpEL 식을 사용해야하며 구문이 복잡해서 사용 빈도수가 적어보이긴하다. (알아두면 유용할듯)
         * ROLE 규칙에만 중점을 둘 필요는 없을듯
         */
//        http.authorizeRequests()
//            .anyRequest().access("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(23, 30))");

        return http.build();
    }
}
