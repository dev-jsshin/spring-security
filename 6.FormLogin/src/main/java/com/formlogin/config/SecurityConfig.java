package com.formlogin.config;

import com.formlogin.handler.CustomAuthenticationFailureHandler;
import com.formlogin.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource sdds = new SimpleDriverDataSource();
        sdds.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        sdds.setUrl("jdbc:mysql://localhost:3306/DB?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8");
        sdds.setUsername("user");
        sdds.setPassword("password");

        return sdds;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from db.users where username = ?";
        String authsByUserQuery = "select username, authority from db.authorities where username = ?";
        var userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
        return userDetailsManager;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*
     * WebSecurityConfigureAdaoter Deprecated 이슈
     * configure 방식에서 SecurityFilterChain 방식 사용
     * 아래 방식으로 성공, 실패 핸들러 객체를 등록할 수 있다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin((formLogin) ->
                        formLogin.successHandler(authenticationSuccessHandler)
                                 .failureHandler(authenticationFailureHandler))
            .authorizeRequests()
                .anyRequest().authenticated();

        return http.build();
    }
}
