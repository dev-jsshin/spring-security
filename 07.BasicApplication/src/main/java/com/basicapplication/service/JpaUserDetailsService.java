package com.basicapplication.service;

import com.basicapplication.entity.User;
import com.basicapplication.model.CustomUserDetails;
import com.basicapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException("계정을 찾을 수 없습니다.");

        User u = userRepository.findUserByUsername(username).orElseThrow(s);

        return new CustomUserDetails(u);
    }
}
