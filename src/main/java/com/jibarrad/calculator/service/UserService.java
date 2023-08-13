package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final int BALANCE = 20;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public UserEntity topUpCredits(UserEntity user) {
        LocalDateTime lastTopUpTime = user.getLastTopUpTime();
        if(lastTopUpTime != null) {
            LocalDateTime fiveSecondsAgo = LocalDateTime.now().minusSeconds(5);

            if(lastTopUpTime.isAfter(fiveSecondsAgo)) {
                throw new RuntimeException("Top-up not allowed. Last top-up was less than 5 seconds ago");
            }
        }

        user.setBalance(BALANCE);
        user.setLastTopUpTime(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }
}
