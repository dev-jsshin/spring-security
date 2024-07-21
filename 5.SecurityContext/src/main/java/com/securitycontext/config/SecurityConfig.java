package com.securitycontext.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableAsync
public class SecurityConfig {

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
     * 각 스레드는 자신의 SecurityContext 에 있는 정보만 접근할 수 있다.
     * @Async 를 통해 새 스레드가 생성될 시
     * End Point 요청 스레드의 SecurityContext는 새 스레드로 복사되지않는다.
     * 아래 설정을 통해 복사할 수 있다.
     * (@Async 지정 시 메소드가 별도의 스레드에서 실행됨, 요청 스레드에서 실행 x)
     */
    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    /*
     * 아래 MODE_GLOBAL 설정은 SecurityContext에 모든 스레드가 접근 가능하다.
     * 이 전략은 독립형 어플리케이션에 적합하다. (웹에서는 적합하지않음)
     * SecurityContext는 스레드 안전에 대해 보장을 하지않으므로 동시 접근에 대한 이슈는 직접 해결해야할듯
     */
//    @Bean
//    public InitializingBean initializingBean() {
//        return () -> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
//    }
}
