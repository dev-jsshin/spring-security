package com.userdetailsmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;


@Configuration
public class SecurityConfig {

    /*
     * WebSecurityConfigureAdaoter Deprecated 이슈
     * 직접 빈 생성을 통해 대응한다.
     */
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource sdds = new SimpleDriverDataSource();
        sdds.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        sdds.setUrl("jdbc:mysql://localhost:3306/DB?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8");
        sdds.setUsername("user");
        sdds.setPassword("password");

        return sdds;
    }

    /*
     * JdbcUserDetailsManager
     * Security에서 만들어주는 TABLE 구조를 사용하지않고 자체 생성한 테이블로 사용할시 아래 방법으로 진
     */
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
}
