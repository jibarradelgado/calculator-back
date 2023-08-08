package com.jibarrad.calculator.service.dto;

import lombok.Data;

@Data
public class DeletedRecordDTO {
    private Long record_id;
    private Boolean deleted;
}
