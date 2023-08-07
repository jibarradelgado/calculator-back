package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.RecordEntity;
import com.jibarrad.calculator.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.base-url}/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) { this.recordService = recordService; }

    @GetMapping("/{operationResult}")
    public ResponseEntity<Page<RecordEntity>> getRecordsLikeOperationResult(@PathVariable String operationResult,
                                                                            @RequestParam Long userId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int elements,
                                                                            @RequestParam(defaultValue = "dateTime") String sortBy,
                                                                            @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ResponseEntity.ok(this.recordService.getAllContainingOperationResult(userId,page,elements,sortBy,sortDirection, operationResult));
    }

    @PatchMapping("/delete")
    public ResponseEntity<RecordEntity> softDelete(@RequestBody RecordEntity record) {
        if(record.getId() != null && this.recordService.exists(record.getId())) {
            record.setDeleted(true);
            return ResponseEntity.ok(this.recordService.save(record));
        }
        return ResponseEntity.badRequest().build();
    }
}
