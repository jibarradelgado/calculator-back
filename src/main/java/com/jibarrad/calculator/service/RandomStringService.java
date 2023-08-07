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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        return record;
    }

    @Transactional
    public String[] getRandomStrings(Long userId, int num, int len, boolean digits, boolean upperAlpha, boolean lowerAlpha, boolean unique) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        OperationEntity operation = operationRepository.findByTypeEquals(OperationEntity.OperationType.RANDOM_STRING.toString());

        if (user == null) {
            throw new RuntimeException("User doesn't exist");
        }
        if (user.getBalance() < operation.getCost()) {
            throw new RuntimeException("Operation rejected. User does not have enough credits.");
        }

        String url = buildRandomApiUrl(num, len, digits, upperAlpha, lowerAlpha, unique);
        ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, null, String[].class);
        String[] stringsFromList = response.getBody();

        RecordEntity record = setRecord(user, operation, String.join(", ", stringsFromList));
        recordRepository.save(record);
        user.setBalance(user.getBalance() - operation.getCost());
        userRepository.save(user);

        return stringsFromList;
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
