package com.businessserver.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
 * 본 어플리케이션의 인증 시나리오는 아이디 패스워드를 입력하고 OTP 코드를 받는다.
 * 그 후 2FA를 통해 최종 JWT를 받는다.
 * 아래는 아이디/패스워드를 위한 Authentication
 *
 * 아래에는 매개변수가 각각 2개, 3개를 가지는 생성자가 있다.
 * 2개인 생성자를 호출하면 인증 인스턴스가 인증되지 않은 상태로 유지된다.
 * 3개인 생성자를 호출하면 Authentication 객체가 최종 인증된다.
 *
 * 인증 시나리오상 아이디 패스워드에 대한 1차 인증을 마친 후 OTP 인증을 통해 최종 인증을 해야하므로 아래와 같이 구성
 */
public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}