package com.jibarrad.calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RandomStringService {
    private final String RANDOM_API_URL = "https://www.random.org/strings/";
    private final String ON = "on";
    private final String OFF = "off";
    private final RestTemplate restTemplate;

    @Autowired
    public RandomStringService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String[] getRandomStrings(int num, int len, boolean digits, boolean upperAlpha, boolean lowerAlpha, boolean unique) {
        String url = buildRandomApiUrl(num, len, digits, upperAlpha, lowerAlpha, unique);
        ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, null, String[].class);
        return response.getBody();
    }

    private String buildRandomApiUrl(int num, int len, boolean digits, boolean upperAlpha, boolean lowerAlpha, boolean unique) {
        StringBuilder urlBuilder = new StringBuilder(RANDOM_API_URL);

        Map<String, String> params = new HashMap<>();
        params.put("num", String.valueOf(num));
        params.put("len", String.valueOf(len));
        params.put("digits", digits ? ON : OFF );
        params.put("upperalpha", upperAlpha ? ON : OFF );
        params.put("loweralpha", lowerAlpha ? ON : OFF );
        params.put("unique", unique ? ON : OFF );
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
