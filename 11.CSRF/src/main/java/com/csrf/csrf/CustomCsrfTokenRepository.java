package com.csrf.csrf;

import com.csrf.entity.Token;
import com.csrf.repository.JpaTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        String uuid = UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String identifier = httpServletRequest.getHeader("X-IDENTIFIER");

        /*
         * 이 어플리케이션은 스프링 시큐리티 기본 로그인 화면을 사용하고 있다.
         * 로그인 시 identifier에 대한 정보를 찾을 수 없으므로 일단 john으로 임시 처리
         * 자체 로그인 화면 개발 시 로그인 할 때 identifier 정보를 같이 넘기면 될 듯
         * /database/init/dml.sql 에 명시된 대로 john 계정을 이용
         */
        if(identifier == null) {
            identifier = "john";
        }

        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {

//            System.out.println("DB Token ==> " + existingToken.get().getToken());
//            System.out.println("csrfToken.getToken() ==> " + csrfToken.getToken());

            // UPDATE 로직 필요
            Token token = existingToken.get();
            token.setToken(csrfToken.getToken());
        } else {
            Token token = new Token();
            token.setToken(csrfToken.getToken());
            token.setIdentifier(identifier);
            jpaTokenRepository.save(token);
        }

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {

        String identifier = httpServletRequest.getHeader("X-IDENTIFIER");

        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
        }

        return null;
    }
}