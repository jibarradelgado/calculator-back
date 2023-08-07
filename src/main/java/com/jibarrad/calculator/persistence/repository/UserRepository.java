package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
