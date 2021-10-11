package edu.depaul.cdm.se452.cryptotradingsimulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
public class AppCacheTest {
    @Autowired
    private AppCacheRepository repository;

    MockApiCall mockApiCall;

    @BeforeEach
    void beforeAll() {
        mockApiCall = new MockApiCall("20190613");
    }

    @Test
    void uncachedItemReturnsIsCachedFalse() {
        assertThat(CacheManager.isCached(mockApiCall.getCacheKey(), repository)).isFalse();
    }

    @Test
    void canCacheItem() throws JsonProcessingException {
        // Make expensive API call and store result in cache
        Integer expirationTimeSeconds = 5;
        CacheManager.cacheItem(mockApiCall.getCacheKey(), mockApiCall.veryExpensiveSlowAPICall(), expirationTimeSeconds, repository);
        System.out.println("repository.findAll() = " + repository.findAll().size());
        System.out.println("repository.findById(\"api_response_20190613\") = " + repository.findById("api_response_20190613"));
        assertThat(CacheManager.isCached(mockApiCall.getCacheKey(), repository)).isTrue();
    }


}
