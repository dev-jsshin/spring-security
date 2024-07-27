package com.userdetails.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class InMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users; // 메모리 내 사용자 목록

    public InMemoryUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }

    /*
     * loadUserByUsername 메소드는 요청된 사용자 이름으로 List<UserDetails> users << 에 존재하는지 검색하고
     * 있으면 UserDeatils 인스턴스를 반환하고 없으면 UsernameNotFoundException 던짐
     * 여기서 요청이라함은 로그인 등이 있겠다..
     * 참고로 UsernameNotFoundException == RuntimeException...
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(u -> u.getUsername().equals(username)) // 요청된 사용자의 이름과 일치하는 항목 필터링
                .findFirst() // 일치하면 인스턴스 반환
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // 사용자 이름이 일치하지 않으면 예외
    }
}
