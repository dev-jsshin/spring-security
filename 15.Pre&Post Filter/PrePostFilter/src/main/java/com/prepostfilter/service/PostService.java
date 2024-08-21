package com.prepostfilter.service;

import com.prepostfilter.model.Document;
import com.prepostfilter.model.Employee;
import com.prepostfilter.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * Post Filter (사후 필터)
 * 비즈니스 로직을 경유한 후 특정 조건에 의해 Return 가능 여부 체크
 * ex) DB에서 데이터를 가지고 온 후 체크
 */
@Service
public class PostService {

    @Autowired
    private DocumentRepository documentRepository;

    private Map<String, Employee> records =
            Map.of("emma",
                    new Employee("Emma Thompson",
                            List.of("Karamazov Brothers"),
                            List.of("accountant", "reader")),
                    "natalie",
                    new Employee("Natalie Parker",
                            List.of("Beautiful Paris"),
                            List.of("researcher"))
            );

    /*
     *  reader 문자가 포함된 경우만 Return Object
     */
    @PostAuthorize("returnObject.roles.contains('reader')")
    public Employee getBookDetails(String name) {
        return records.get(name);
    }

    /*
     * ROLE_admin 인 경우에만 Return Object
     * SpEL 식은 권장되지 않으며 복잡한 권한 부여 규칙을 구현해야할때는 별도의 클래스로 논리를 구현해야한다.
     */
    @PostAuthorize("hasPermission(returnObject, 'ROLE_admin')")
    public Document getDocument(String code) {
        return documentRepository.findDocument(code);
    }
}
