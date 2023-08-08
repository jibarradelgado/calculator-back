package com.jibarrad.calculator.persistence.repository;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RecordPageSortRepository extends ListPagingAndSortingRepository <RecordEntity, Long> {
    Page<RecordEntity> findByDeletedFalse(Pageable pageable);

    Page<RecordEntity> findByDeletedFalseAndOperationResponseContainingIgnoreCase(Pageable pageable, String operationResponse);

    @Query(value =
            "SELECT * FROM record " +
            "WHERE deleted = false " +
            "AND LOWER(operation_response) LIKE %:operationResponse% " +
            "AND user_id = :userId", nativeQuery = true)
    Page<RecordEntity> findRecordsByOperationResponseAndUserId(Pageable pageable, @Param("operationResponse") String operationResponse, @Param("userId") Long userId);

}
