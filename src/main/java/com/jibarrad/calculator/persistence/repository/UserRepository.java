package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {
}
