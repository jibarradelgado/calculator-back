package com.jibarrad.calculator.service;

import com.jibarrad.calculator.persistence.entity.OperationEntity;
import com.jibarrad.calculator.persistence.entity.RecordEntity;
import com.jibarrad.calculator.persistence.entity.UserEntity;
import com.jibarrad.calculator.persistence.repository.OperationRepository;
import com.jibarrad.calculator.persistence.repository.RecordRepository;
import com.jibarrad.calculator.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RandomStringService {
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final OperationRepository operationRepository;
    private final RecordRepository recordRepository;


    @Autowired
    public RandomStringService(UserRepository userRepository, OperationRepository operationRepository, RecordRepository recordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.operationRepository = operationRepository;
        this.recordRepository = recordRepository;
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        this.restTemplate = restTemplate;
    }

    private RecordEntity setRecord(UserEntity user, OperationEntity operation, String result) {
        RecordEntity record = new RecordEntity();
        record.setUser(user);
        record.setOperation(operation);
        record.setDateTime(LocalDateTime.now());
        record.setOperationResponse(result);
        record.setBalanceBeforeOperation(user.getBalance());
        record.setBalanceAfterOperation(user.getBalance() - operation.getCost());
        record.setDeleted(false);
        return record;
    }

    @Transactional
    public String[] getRandomStrings(Long userId, int num, int len, boolean digits, boolean upperAlpha, boolean lowerAlpha, boolean unique) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        Optional<OperationEntity> operation = operationRepository.findByType(OperationEntity.OperationType.RANDOM_STRING);

        if (user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        if (operation.isEmpty()) {
            throw new RuntimeException("Operation doesn't exist in database");
        }
        if (user.getBalance() < operation.get().getCost()) {
            throw new RuntimeException("Operation rejected. User does not have enough credits.");
        }

        String url = buildRandomApiUrl(num, len, digits, upperAlpha, lowerAlpha, unique);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String response = responseEntity.getBody();

        String[] strings = response.trim().split("\\r?\\n");

        RecordEntity record = setRecord(user, operation.get(), String.join(", ", strings));
        recordRepository.save(record);
        user.setBalance(user.getBalance() - operation.get().getCost());
        userRepository.save(user);

        return strings;
    }

    private String buildRandomApiUrl(int num, int len, boolean digits, boolean upperAlpha, boolean lowerAlpha, boolean unique) {
        String RANDOM_API_URL = "https://www.random.org/strings/";
        String ON = "on";
        String OFF = "off";
        StringBuilder urlBuilder = new StringBuilder(RANDOM_API_URL);

        Map<String, String> params = new HashMap<>();
        params.put("num", String.valueOf(num));
        params.put("len", String.valueOf(len));
        params.put("digits", digits ? ON : OFF);
        params.put("upperalpha", upperAlpha ? ON : OFF);
        params.put("loweralpha", lowerAlpha ? ON : OFF);
        params.put("unique", unique ? ON : OFF);
        //hardcoded a pair of params.
        params.put("format", "plain" );
        params.put("rnd", "new" );

        int paramIndex = 0;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.append(paramIndex == 0 ? "?" : "&" );
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            paramIndex++;
        }

        return urlBuilder.toString();
    }
}
