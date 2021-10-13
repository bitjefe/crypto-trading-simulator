package edu.depaul.cdm.se452.cryptotradingsimulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

class MockApiCall {
    private String date;

    ObjectMapper mapper = new ObjectMapper();

    public MockApiCall(String date) {
        this.date = date;
    }

    public String getCacheKey() {
        return String.format("api_response_%s", date);
    }

    public String veryExpensiveSlowAPICall() throws JsonProcessingException {
        // Very slow API call that only grants us so many requests per day ...
        Map<String, Double> apiResponse = Map.of("btc", 1000.00, "doge", 2000.00);
        return mapper.writeValueAsString(apiResponse);
    }
}