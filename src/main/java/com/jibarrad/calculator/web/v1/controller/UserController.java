package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.service.UserService;
import com.jibarrad.calculator.service.dto.UserBalanceDTO;
import com.jibarrad.calculator.service.dto.UserDTO;
import com.jibarrad.calculator.web.config.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base-url}/users")
public class UserController {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String jwtToken = authHeader.substring("Bearer ".length());

            String username = jwtUtil.getUserName(jwtToken);

            UserEntity user = this.userService.getUser(username);
            if (user == null) {
                return ResponseEntity.badRequest().body("User doesn't exist");
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getIdUser());
            userDTO.setUsername(user.getUsername());
            userDTO.setBalance(user.getBalance());
            return ResponseEntity.ok(userDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topUpCredits(@RequestParam Long userId) {
        try {
            UserEntity user = this.userService.getUser(userId);
            if(user == null) {
                return ResponseEntity.badRequest().body("User doesn't exist");
            }
            UserEntity userEntity = this.userService.topUpCredits(user);
            UserBalanceDTO userBalanceDTO = new UserBalanceDTO();
            userBalanceDTO.setUsername(userEntity.getUsername());
            userBalanceDTO.setBalance(userEntity.getBalance());
            return ResponseEntity.ok(userBalanceDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
