package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import com.jibarrad.calculator.persistence.entity.RecordEntity;
import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.persistence.repository.OperationRepository;
import com.jibarrad.calculator.persistence.repository.RecordRepository;
import com.jibarrad.calculator.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CalculatorService {

    private final UserRepository userRepository;
    private final OperationRepository operationRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public CalculatorService(UserRepository userRepository, OperationRepository operationRepository, RecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
        this.recordRepository = recordRepository;
    }

    private RecordEntity setRecord(UserEntity user, OperationEntity operation, String result) {
        RecordEntity record = new RecordEntity();
        record.setUser(user);
        record.setOperation(operation);
        record.setDateTime(LocalDateTime.now());
        record.setOperationResponse(result);
        record.setBalanceBeforeOperation(user.getBalance());
        record.setBalanceAfterOperation(user.getBalance() - operation.getCost());
        return record;
    }

    @Transactional
    public double performArithmeticOperation(Long userId, double num1, double num2, OperationEntity.OperationType operationType) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        OperationEntity operation = operationRepository.findByTypeEquals(OperationEntity.OperationType.ADDITION.toString());

        if (user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        if (user.getBalance() < operation.getCost()) {
            throw new RuntimeException("Operation rejected. User does not have enough credits.");
        }

        double result = 0;
        switch (operationType) {
            case ADDITION -> result = num1 + num2;
            case SUBTRACTION -> result = num1 - num2;
            case MULTIPLICATION -> result = num1 * num2;
            case DIVISION -> {
                if(num2 == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed.");
                }
                result = num1 / num2;
            }
            case SQUARE_ROOT -> {
                if(num1 < 0) {
                    throw new IllegalArgumentException("Square root of a negative number is not allowed.");
                }
                result = Math.sqrt(num1);
            }
        }

        RecordEntity record = setRecord(user, operation, String.valueOf(result));

        recordRepository.save(record);
        user.setBalance(user.getBalance() - operation.getCost());
        userRepository.save(user);

        return result;
    }
}
