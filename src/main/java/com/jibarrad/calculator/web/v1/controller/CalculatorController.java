package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import com.jibarrad.calculator.service.CalculatorService;
import com.jibarrad.calculator.service.dto.CalculatorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.base-url}/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/addition")
    public ResponseEntity<?> performAddition(@RequestBody CalculatorRequest request) {
        try {
            double result = calculatorService.performArithmeticOperation(request.getUserId(), request.getNum1(), request.getNum2(), OperationEntity.OperationType.ADDITION);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/subtraction")
    public ResponseEntity<?> performSubtraction(@RequestBody CalculatorRequest request) {
        try{
            double result = calculatorService.performArithmeticOperation(request.getUserId(), request.getNum1(), request.getNum2(), OperationEntity.OperationType.SUBTRACTION);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/multiplication")
    public ResponseEntity<?> performMultiplication(@RequestBody CalculatorRequest request) {
        try {
            double result = calculatorService.performArithmeticOperation(request.getUserId(), request.getNum1(), request.getNum2(), OperationEntity.OperationType.MULTIPLICATION);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @PostMapping("/division")
    public ResponseEntity<?> performDivision(@RequestBody CalculatorRequest request) {
        try {
            double result = calculatorService.performArithmeticOperation(request.getUserId(), request.getNum1(), request.getNum2(), OperationEntity.OperationType.DIVISION);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/square_root")
    public ResponseEntity<?> performSquareRoot(@RequestBody CalculatorRequest request) {
        try {
            double result = calculatorService.performArithmeticOperation(request.getUserId(), request.getNum1(), request.getNum2(), OperationEntity.OperationType.SUBTRACTION);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
