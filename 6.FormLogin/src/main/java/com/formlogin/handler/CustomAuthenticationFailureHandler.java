package com.formlogin.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /*
     * AuthenticationFailureHandler 로그인 실패 이후 세부적인 설정이 가능하다.
     * 로그인 실패 5회 시 계정 잠금 처리 같은 논리를 구현할 수 있다.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)  {
        httpServletResponse.setHeader("failed", LocalDateTime.now().toString());
    }
}
