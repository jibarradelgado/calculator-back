package com.jibarrad.calculator.service.dto;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordDTO {
    private Long record_id;
    private OperationEntity.OperationType operationType;
    private String username;
    private Long user_id;
    private Integer balance_before;
    private Integer balance_after;
    private String operationResponse;
    private Boolean deleted;
    private LocalDateTime dateTime;
}
