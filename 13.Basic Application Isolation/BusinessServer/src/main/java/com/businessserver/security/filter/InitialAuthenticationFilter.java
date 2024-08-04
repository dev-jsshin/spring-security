package com.businessserver.security.filter;

import com.businessserver.security.authentication.OtpAuthentication;
import com.businessserver.security.authentication.UsernamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    // AuthenticationProvider 관리
    private final AuthenticationManager manager;

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        /*
         * code가 없으면 1차 인증으로 판단하고 있으면 2차 인증으로 판단한다.
         */
        if (code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(a); // UsernamePasswordAuthentication으로 AuthenticationManager 호출
        } else {
            Authentication a = new OtpAuthentication(username, code);
            manager.authenticate(a); // OtpAuthentication으로 AuthenticationManager 호출

            /*
             * 2FA 완료 시 JWT를 Resonse (Header)
             * OtpAuthenticationProvider 에서 porxy를 통해 인증 서버를 Call 할 때 403이 아닌 200 코드를 받으면
             * 아래 로직 수행
             */
            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .signWith(key)
                    .compact();
            response.setHeader("Authorization", jwt);
        }

    }

    /*
     * /login 엔드포인트만 InitialAuthenticationFilter를 경유한다.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}