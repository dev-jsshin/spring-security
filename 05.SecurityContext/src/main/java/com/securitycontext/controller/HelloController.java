package com.securitycontext.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    /*
     * http://localhost:8080/hello
     * john:12345 (base64)
     *
     * 인증 프로세스가 끝난 후 인증된 엔티티에 대해 세부 정보가 필요할 경우가 있는데
     * 인증 후 AuthenticationManager는 Authentication 인스턴트를 저장한다.(SecurityContext)
     * 스프링부트는 매개변수에 Authentication 자동 주입할 수 있게 지원함
     */
    @GetMapping("/hello")
    public String hello(Authentication a) {
        return a.getName();
    }

    /*
     * http://localhost:8080/hello/async1
     * john:12345 (base64)
     *
     * @Async를 지정하면 요청 스레드가 아닌 비동기 스레드가 아래 메소드를 실행되는데
     * 일반적으론 요청 스레드가 SecurityContext를 비동기 스레드로 복사하지않는다.
     * SecurityConfig 내 관련 설정이 있음 (설정 안하면 NPE 발생)
     */
    @GetMapping("/hello/async1")
    @Async
    public void helloAsync1() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
    }

    /*
     * http://localhost:8080/hello/async2
     * john:12345 (base64)
     *
     * 자체 관리 스레드 방식
     * 아래처럼 @Async를 지정하지않고 자체적으로 관리할 수 있는 방법도 있음
     * SecurityContext를 자체적으로 전파해야함
     * DelegatingSecurityContextCallable을 통해 비동기 스레드에 SecurityContext를 전파하는 방식
     */
    @GetMapping("/hello/async2")
    public String helloAsync2() throws Exception {
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };

        ExecutorService e = Executors.newCachedThreadPool();
        try {
            var contextTask = new DelegatingSecurityContextCallable<>(task);
            return e.submit(contextTask).get();
        } finally {
            e.shutdown();
        }
    }

    /*
     * http://localhost:8080/hello/async3
     * john:12345 (base64)
     *
     * 자체 관리 스레드 방식
     * 아래처럼 @Async를 지정하지않고 자체적으로 관리할 수 있는 방법도 있음
     * SecurityContext를 자체적으로 전파해야함
     * DelegatingSecurityContextExecutorService를 통해
     * 작업에서 SecurityContext를 복사하지않고 스레드 풀에서 전파 관리하는 방식
     */
    @GetMapping("/hello/async3")
    public String helloAsync3() throws Exception {
        Callable<String> task = () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };

        ExecutorService e = Executors.newCachedThreadPool();
        e = new DelegatingSecurityContextExecutorService(e);
        try {
            return e.submit(task).get();
        } finally {
            e.shutdown();
        }
    }
}

