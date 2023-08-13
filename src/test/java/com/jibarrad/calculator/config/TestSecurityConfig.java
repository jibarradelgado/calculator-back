package com.jibarrad.calculator.config;

import com.jibarrad.calculator.web.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@Import(SecurityConfig.class)
@EnableMethodSecurity(securedEnabled = true)
public class TestSecurityConfig {
}
