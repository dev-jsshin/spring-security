package com.businessserver.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/*
 * 본 어플리케이션의 인증 시나리오는 아이디 패스워드를 입력하고 OTP 코드를 받는다.
 * 그 후 2FA를 통해 최종 JWT를 받는다.
 * 아래는 아이디/패스워드를 위한 Authentication
 *
 * UsernamePasswordAuthentication은 매개변수가 2개, 3개로 구성된 생성자가 있지만
 * OTP를 암호로 가정할 수 있어서 2개로 구성
 */
public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}