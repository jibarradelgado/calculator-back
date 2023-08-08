package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.service.UserService;
import com.jibarrad.calculator.service.dto.UserBalanceDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.base-url}/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

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
