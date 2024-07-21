package com.formlogin.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /*
     * AuthenticationSuccessHandler는 로그인 이후 세부적인 설정이 가능하다.
     * 아래 코드는 read 권한이 있을 경우 /home 경로로 리다이렉트하고
     * 권한이 없으면 /error 로 리다이렉트 한다.
     *
     * 신규 프로젝트 개발 당시 계정 권한별 리다이렉트 경로를 Controller 단에 책임을 부여했는데
     * 여기서 설정하는게 Spring Security Pattern 상 좋을 거 같음
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        var authorities = authentication.getAuthorities();

        var auth = authorities.stream()
                .filter(a -> a.getAuthority().equals("read"))
                .findFirst();

        if (auth.isPresent()) {
            httpServletResponse.sendRedirect("/home");
        } else {
            httpServletResponse.sendRedirect("/error");
        }
    }
}