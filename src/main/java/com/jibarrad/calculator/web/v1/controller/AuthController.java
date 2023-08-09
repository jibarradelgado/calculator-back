package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.persistence.repository.UserRepository;
import com.jibarrad.calculator.service.UserService;
import com.jibarrad.calculator.service.dto.LoginRequest;
import com.jibarrad.calculator.web.config.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("${api.v1.base-url}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Set the content type to JSON
        headers.set(HttpHeaders.AUTHORIZATION, jwt); // Set the Authorization header

        UserEntity userEntity = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        if(userEntity != null) {
            userEntity.setLastLogin(LocalDateTime.now());
            userRepository.save(userEntity);
        }

        return ResponseEntity.ok().headers(headers).body("{\"message\": \"Login successful\"}");
    }
}
