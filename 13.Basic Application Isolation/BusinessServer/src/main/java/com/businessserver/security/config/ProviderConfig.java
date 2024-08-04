package com.businessserver.security.config;

import com.businessserver.security.provider.OtpAuthenticationProvider;
import com.businessserver.security.provider.UsernamePasswordAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.Arrays;

@Configuration
public class ProviderConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    private final OtpAuthenticationProvider otpAuthenticationProvider;

    public ProviderConfig(UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider, OtpAuthenticationProvider otpAuthenticationProvider) {
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(this.usernamePasswordAuthenticationProvider, this.otpAuthenticationProvider));
    }
}