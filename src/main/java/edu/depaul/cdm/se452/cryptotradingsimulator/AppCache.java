package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "app_cache")
public class AppCache {
    @Id
    private String cacheKey;

    private String content;

    private LocalDateTime expiresAt;

    public Boolean isExpired() {
        return LocalDateTime.now().isBefore(expiresAt);
    }
}
