package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OperationRepository extends ListCrudRepository<OperationEntity, Long> {
}
