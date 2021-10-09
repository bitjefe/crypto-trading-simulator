package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

public class CacheManager {
    static Boolean isCached(String cacheKey) {
        return false;
    }

    static String getCacheValue(String cacheKey, AppCacheRepository repository) {
        Optional<AppCache> appCache = repository.findById(cacheKey);
        if (appCache.isPresent() && !appCache.get().isExpired()) {
            return appCache.get().getContent();
        }

        return null;
    }

    static void cacheItem(String cacheKey, String cacheContent, Integer expirationTimeSeconds, AppCacheRepository repository) {
        AppCache appCache = new AppCache();
        appCache.setCacheKey(cacheKey);
        appCache.setContent(cacheContent);
        appCache.setExpiresAt(LocalDateTime.now().plusSeconds(expirationTimeSeconds));
        System.out.println("repository = " + repository);
        System.out.println("appCache = " + appCache);
        repository.save(appCache);
    }
}
