package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RecordRepository extends ListCrudRepository<RecordEntity, Long> {
    List<RecordEntity> findAllByDeletedFalse();
}
