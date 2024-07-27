package com.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthenticationLoggingFilter implements Filter {

    private final Logger logger = Logger.getLogger(AuthenticationLoggingFilter.class.getName());

    /*
     * 인증 프로세스 이후에 대한 필터를 정의했다.
     * 로깅 추적 목적을 위해 사용하면 좋을듯
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        String requestId = httpRequest.getHeader("Request-Id");
        logger.info("Login Success Request-Id : " +  requestId);
        filterChain.doFilter(request, response);
    }
}