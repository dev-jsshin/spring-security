package com.prepostauthorize.service;

import com.prepostauthorize.model.Document;
import com.prepostauthorize.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * Pre Authorize (사전 권한부여)
 * 특정 조건에 의해서 비즈니스 로직을 경유하면 안될 때 사용
 */
@Service
public class PreService {

    @Autowired
    private DocumentRepository documentRepository;

    private Map<String, List<String>> secretNames = Map.of("natalie", List.of("Energico", "Perfecto"),
                                                           "emma", List.of("Fantastico"));

    /*
     * write 권한이 있는 계정만 아래 비즈니스 로직을 경유한다.
     */
    @PreAuthorize("hasAuthority('write')")
    public String getName() {
        return "emma";
    }

    /*
     * 로그인 계정과 매개 변수 name이 같을 경우 아래 비즈니스 로직을 경유한다.
     */
    @PreAuthorize("#name == authentication.principal.username")
    public List<String> getSecretNames(String name) {
        return secretNames.get(name);
    }

    /*
     * 로그인 계정과 code가 일치할 경우만 아래 비즈니스 로직 경우
     * SpEL 식은 권장되지 않으며 복잡한 권한 부여 규칙을 구현해야할때는 별도의 클래스로 논리를 구현해야한다.
     */
    @PreAuthorize("hasPermission(#code, 'document', 'ROLE_admin')")
    public Document getDocument(String code) {
        return documentRepository.findDocument(code);
    }
}
