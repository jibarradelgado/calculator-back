package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import com.jibarrad.calculator.persistence.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public List<OperationEntity> getAll() { return this.operationRepository.findAll(); }
}
