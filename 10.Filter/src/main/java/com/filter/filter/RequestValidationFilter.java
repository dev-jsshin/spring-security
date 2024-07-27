package com.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

public class RequestValidationFilter implements Filter {

    private final Logger logger = Logger.getLogger(RequestValidationFilter.class.getName());

    /*
     * 인증 프로세스 전에 필터 하나를 추가했다.
     * 이 필터는 Header에 Request-Id : keeyyyyy 가 있는지 확인한다.
     * HTTP Basic 인증 방식 말고 다른 인증 방식을 정의해서 사용할 수 있을 거 같다.
     * 사내 내부망 통신 등 간단한 보안만 적용해도 될 경우 IP 인증 방식, Key 인증 방식 등을 사용해도 될거같다.
     *
     * 로깅 추적 목적을 위해 사용하면 좋을듯
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        String requestId = httpRequest.getHeader("Request-Id");

        logger.info("Login Request Request-Id : " +  requestId);

        if (requestId == null || requestId.isBlank()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if(!requestId.equals("keeyyyyy")) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
