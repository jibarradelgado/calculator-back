package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import com.jibarrad.calculator.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.v1.base-url}/operations")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @GetMapping
    public ResponseEntity<List<OperationEntity>> getAll() { return ResponseEntity.ok(this.operationService.getAll()); }
}
