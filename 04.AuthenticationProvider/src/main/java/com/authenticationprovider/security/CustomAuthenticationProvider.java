package com.authenticationprovider.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * authenticate() 메소드는 Authentication 객체를 매개 변수로 받고 Authentication 객체를 반환한다. (아래 인증 논리를 통해 검증된 객체만)
     * isAuthenticated() -> true로 반환되고 패스워드 같은 민감한 데이터는 자동으로 제거됨
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails u = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, u.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, u.getAuthorities());
        } else {
            throw new BadCredentialsException("인증 실패");
        }
    }

    /*
     * 인증 유형에 대해 정의하는 메소드
     * authenticate() 메소드를 통해 Authentication 객체가 반환되도
     * supports() 에서 정의내린 인증 유형이 맞는지 확인
     *
     * 인증 유형은 맞지만 입력된 사용자 정보가 DB에 없다면 Authentication : null / supports : true 예상....
     */
    @Override
    public boolean supports(Class<?> authenticationType) {
        // 사용자 이름과 암호를 이용하는 표준 인증 요청을 나타냄 (특이사항이 없으면 대부분 아래 인증 유형으로 설정될듯)
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }

}
