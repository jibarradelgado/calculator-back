package com.jibarrad.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double addition(double num1, double num2) {
        return num1 + num2;
    }

    public double subtraction(double num1, double num2) {
        return num1 - num2;
    }

    public double multiplication(double num1, double num2) {
        return num1 * num2;
    }

    public double division(double num1, double num2) {
        if(num2 == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return num1 / num2;
    }

    public double squareRoot(double num) {
        if(num < 0) {
            throw new IllegalArgumentException("Square root of a negative number is not allowed.");
        }
        return Math.sqrt(num);
    }
}
