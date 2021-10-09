package edu.depaul.cdm.se452.cryptotradingsimulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnableMongoRepositories
public class AppCacheTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppCacheRepository repository;

    MockApiCall mockApiCall;

    @BeforeEach
    void beforeAll() {
        mockApiCall = new MockApiCall("20190613");
    }

    @Test
    void uncachedItemReturnsIsCachedFalse() {
        System.out.println("repository = " + repository);
        assertThat(CacheManager.isCached(mockApiCall.getCacheKey())).isFalse();
    }

    @Test
    void canCacheItem() throws JsonProcessingException {
        // Make expensive API call and store result in cache
        Integer expirationTimeSeconds = 5;
        CacheManager.cacheItem(mockApiCall.getCacheKey(), mockApiCall.veryExpensiveSlowAPICall(), expirationTimeSeconds, repository);
        assertThat(CacheManager.isCached(mockApiCall.getCacheKey())).isTrue();
    }

    private class MockApiCall {
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
}
