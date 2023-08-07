package com.jibarrad.calculator.service.dto;

import lombok.Data;

@Data
public class RandomStringRequest {
    private Long userId;
    private int num;
    private int len;
    private boolean digits;
    private boolean upperAlpha;
    private boolean lowerAlpha;
    private boolean unique;
}
