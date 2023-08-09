package com.jibarrad.calculator.service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private Integer balance;
}
