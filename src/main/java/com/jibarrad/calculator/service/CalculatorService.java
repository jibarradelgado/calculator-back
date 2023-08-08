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
import java.util.Optional;

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
        record.setDeleted(false);
        return record;
    }

    private static double getResult(double num1, double num2, OperationEntity.OperationType operationType) {
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
        return result;
    }

    private static String getOperation(double num1, double num2, OperationEntity.OperationType operationType) {
        String operation = "";
        switch (operationType) {
            case ADDITION -> operation = num1 + " + " + num2 + " = ";
            case SUBTRACTION -> operation = num1 + " - " + num2 + " = ";
            case MULTIPLICATION -> operation = num1 + " * " + num2 + " = ";
            case DIVISION -> {
                if(num2 == 0) {
                    throw new IllegalArgumentException("Division by zero is not allowed.");
                }
                operation = num1 + " / " + num2 + " = ";
            }
            case SQUARE_ROOT -> {
                if(num1 < 0) {
                    throw new IllegalArgumentException("Square root of a negative number is not allowed.");
                }
                operation = "√" + num1 + " = ";
            }
        }
        return operation;
    }

    @Transactional
    public double performArithmeticOperation(Long userId, double num1, double num2, OperationEntity.OperationType operationType) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        Optional<OperationEntity> operation = operationRepository.findByType(operationType);

        if (user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        if (operation.isEmpty()) {
            throw new RuntimeException("Operation doesn't exist in database");
        }
        if (user.getBalance() < operation.get().getCost()) {
            throw new RuntimeException("Operation rejected. User does not have enough credits.");
        }

        double result = getResult(num1, num2, operationType);
        String operationString = getOperation(num1,num2,operationType);

        RecordEntity record = setRecord(user, operation.get(), operationString + String.valueOf(result));

        recordRepository.save(record);
        user.setBalance(user.getBalance() - operation.get().getCost());
        userRepository.save(user);

        return result;
    }


}
