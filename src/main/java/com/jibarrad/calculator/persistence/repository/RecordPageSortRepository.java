package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface RecordPageSortRepository extends ListPagingAndSortingRepository <RecordEntity, Long> {
    Page<RecordEntity> findByDeletedFalse(Pageable pageable);
}
