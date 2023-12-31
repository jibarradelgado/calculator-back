package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import com.jibarrad.calculator.persistence.repository.RecordPageSortRepository;
import com.jibarrad.calculator.persistence.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final RecordPageSortRepository recordPageSortRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository, RecordPageSortRepository recordPageSortRepository) {
        this.recordRepository = recordRepository;
        this.recordPageSortRepository = recordPageSortRepository;
    }

    public Page<RecordEntity> getAll(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.recordPageSortRepository.findByDeletedFalse(pageRequest);
    }

    public Page<RecordEntity> getAllContainingOperationResult(int page, int elements, String sortBy, String sortDirection, String operationResult) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.recordPageSortRepository.findByDeletedFalseAndOperationResponseContainingIgnoreCase(pageRequest, operationResult);
    }

    public Page<RecordEntity> getAllContainingOperationResult(Long userId, int page, int elements, String sortBy, String sortDirection, String operationResult) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return this.recordPageSortRepository.findRecordsByOperationResponseAndUserId(pageRequest, operationResult, userId);
    }

    public RecordEntity getById(Long recordId) {
        return this.recordRepository.findById(recordId).orElse(null);
    }

    public Boolean exists(Long recordId) {
        return  this.recordRepository.existsById(recordId);
    }

    public RecordEntity save(RecordEntity record) {
        return this.recordRepository.save(record);
    }

}
