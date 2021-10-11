package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "app_cache")
public class AppCache {
    @Id
    private String id;

    private String content;

    private LocalDateTime expiresAt;

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    static Boolean isCached(String cacheKey, AppCacheRepository repository) {
        Optional<AppCache> appCache = repository.findById(cacheKey);
        System.out.println("appCache = " + appCache);
        return appCache.isPresent() && !appCache.get().isExpired();
    }

    static String getCacheValue(String cacheKey, AppCacheRepository repository) {
        Optional<AppCache> appCache = repository.findById(cacheKey);
        return appCache.get().getContent();
    }

    static void cacheItem(String cacheKey, String cacheContent, Integer expirationTimeSeconds, AppCacheRepository repository) {
        Optional<AppCache> oldItem = repository.findById(cacheKey);
        if (oldItem.isPresent()) {
            repository.delete(oldItem.get());
        }

        AppCache appCache = new AppCache();
        appCache.setId(cacheKey);
        appCache.setContent(cacheContent);
        appCache.setExpiresAt(LocalDateTime.now().plusSeconds(expirationTimeSeconds));
        repository.save(appCache);
    }
}
