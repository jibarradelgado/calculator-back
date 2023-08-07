package com.jibarrad.calculator.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = false)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizeRequests -> {
                    try {
                        customizeRequests
                                .anyRequest()
                                .authenticated()
                                .and().httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
