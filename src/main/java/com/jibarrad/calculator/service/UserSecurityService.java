package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.persistence.entity.UserRoleEntity;
import com.jibarrad.calculator.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found."));

        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles)
                .disabled(userEntity.getStatus() == UserEntity.Status.INACTIVE)
                .build();
    }
}
