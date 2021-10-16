
package edu.depaul.cdm.se452.cryptotradingsimulator.dto.CoinMCResponse;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price",
    "volume_24h",
    "volume_change_24h",
    "percent_change_1h",
    "percent_change_24h",
    "percent_change_7d",
    "percent_change_30d",
    "percent_change_60d",
    "percent_change_90d",
    "market_cap",
    "market_cap_dominance",
    "fully_diluted_market_cap",
    "last_updated"
})
@Generated("jsonschema2pojo")
@Data
public class Usd {

    @JsonProperty("price")
    public Double price;
    @JsonProperty("volume_24h")
    public Double volume24h;
    @JsonProperty("volume_change_24h")
    public Double volumeChange24h;
    @JsonProperty("percent_change_1h")
    public Double percentChange1h;
    @JsonProperty("percent_change_24h")
    public Double percentChange24h;
    @JsonProperty("percent_change_7d")
    public Double percentChange7d;
    @JsonProperty("percent_change_30d")
    public Double percentChange30d;
    @JsonProperty("percent_change_60d")
    public Double percentChange60d;
    @JsonProperty("percent_change_90d")
    public Double percentChange90d;
    @JsonProperty("market_cap")
    public Double marketCap;
    @JsonProperty("market_cap_dominance")
    public Double marketCapDominance;
    @JsonProperty("fully_diluted_market_cap")
    public Double fullyDilutedMarketCap;
    @JsonProperty("last_updated")
    public String lastUpdated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
