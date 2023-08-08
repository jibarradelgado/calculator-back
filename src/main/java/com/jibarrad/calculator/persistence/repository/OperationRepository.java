package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface OperationRepository extends ListCrudRepository<OperationEntity, Long> {
    Optional<OperationEntity> findByType(OperationEntity.OperationType operationType);
}
