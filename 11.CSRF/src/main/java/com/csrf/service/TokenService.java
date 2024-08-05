package com.csrf.service;

import com.csrf.entity.Token;
import com.csrf.repository.JpaTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class TokenService {

    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    /*
     * JPA 영속성 관련으로 TokenService 분리 처리
     */
    public void saveToken(String identifier, String csrfToken) {

        Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);

        if (existingToken.isPresent()) {
            Token token = existingToken.get();
            token.setToken(csrfToken);
        } else {
            Token token = new Token();
            token.setToken(csrfToken);
            token.setIdentifier(identifier);
            jpaTokenRepository.save(token);
        }
    }
}
