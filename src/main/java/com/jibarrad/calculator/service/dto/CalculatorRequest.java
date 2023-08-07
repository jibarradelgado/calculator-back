package com.jibarrad.calculator.service.dto;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import lombok.Data;

@Data
public class CalculatorRequest {
    private Long userId;
    private double num1;
    private double num2;
}
