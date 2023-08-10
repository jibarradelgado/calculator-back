package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import com.jibarrad.calculator.service.RecordService;
import com.jibarrad.calculator.service.dto.DeletedRecordDTO;
import com.jibarrad.calculator.service.dto.RecordDTO;
import com.jibarrad.calculator.service.dto.RecordToDeleteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.v1.base-url}/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) { this.recordService = recordService; }

    @GetMapping("")
    public ResponseEntity<Page<RecordDTO>> getRecordsLikeOperationResult(@RequestParam(defaultValue = "") String operationResult,
                                                                            @RequestParam Long userId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int elements,
                                                                            @RequestParam(defaultValue = "date_time") String sortBy,
                                                                            @RequestParam(defaultValue = "DESC") String sortDirection) {
        Page<RecordEntity> recordEntityPage = this.recordService.getAllContainingOperationResult(userId,page,elements,sortBy,sortDirection, operationResult);
        Page<RecordDTO> records = recordEntityPage.map(record -> {
            RecordDTO recordDTO = new RecordDTO();
            recordDTO.setRecord_id(record.getId());
            recordDTO.setOperationType(record.getOperation().getType());
            recordDTO.setUsername(record.getUser().getUsername());
            recordDTO.setUser_id(record.getUser().getIdUser());
            recordDTO.setBalanceBefore(record.getBalanceBeforeOperation());
            recordDTO.setBalanceAfter(record.getBalanceAfterOperation());
            recordDTO.setOperationResponse(record.getOperationResponse());
            recordDTO.setDeleted(record.getDeleted());
            recordDTO.setDateTime(record.getDateTime());
            return recordDTO;
        });
        return ResponseEntity.ok(records);
    }

    @PatchMapping("/delete")
    public ResponseEntity<DeletedRecordDTO> softDelete(@RequestBody RecordToDeleteDTO record) {
        if(this.recordService.exists(record.getRecordId())) {
            RecordEntity recordToDelete = this.recordService.getById(record.getRecordId());
            recordToDelete.setDeleted(true);
            RecordEntity deletedRecord = this.recordService.save(recordToDelete);
            DeletedRecordDTO deletedRecordDTO = new DeletedRecordDTO();
            deletedRecordDTO.setDeleted(deletedRecord.getDeleted());
            deletedRecordDTO.setRecord_id(deletedRecord.getId());
            return ResponseEntity.ok(deletedRecordDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
