package com.jibarrad.calculator.web.v1.controller;

import com.jibarrad.calculator.service.RandomStringService;
import com.jibarrad.calculator.service.dto.RandomStringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.v1.base-url}/randomstring")
public class RandomStringController {
    private final RandomStringService randomStringService;

    @Autowired
    public RandomStringController(RandomStringService randomStringService) { this.randomStringService = randomStringService; }

    @PostMapping()
    public ResponseEntity<?> getRandomString(@RequestBody RandomStringRequest request) {
        try {
            String[] result = randomStringService.getRandomStrings(
                    request.getUserId(),
                    request.getNum(),
                    request.getLen(),
                    request.isDigits(),
                    request.isUpperAlpha(),
                    request.isLowerAlpha(),
                    request.isUnique());
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
