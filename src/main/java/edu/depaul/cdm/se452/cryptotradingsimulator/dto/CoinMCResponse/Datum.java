
package edu.depaul.cdm.se452.cryptotradingsimulator.dto.CoinMCResponse;

import java.util.HashMap;
import java.util.List;
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
    "id",
    "name",
    "symbol",
    "slug",
    "num_market_pairs",
    "date_added",
    "tags",
    "max_supply",
    "circulating_supply",
    "total_supply",
    "platform",
    "cmc_rank",
    "last_updated",
    "quote"
})
@Generated("jsonschema2pojo")
@Data
public class Datum {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("symbol")
    public String symbol;
    @JsonProperty("slug")
    public String slug;
    @JsonProperty("num_market_pairs")
    public Integer numMarketPairs;
    @JsonProperty("date_added")
    public String dateAdded;
    @JsonProperty("tags")
    public List<String> tags = null;
    @JsonProperty("max_supply")
    public Object maxSupply;
    @JsonProperty("circulating_supply")
    public Double circulatingSupply;
    @JsonProperty("total_supply")
    public Double totalSupply;
    @JsonProperty("platform")
    public Object platform;
    @JsonProperty("cmc_rank")
    public Integer cmcRank;
    @JsonProperty("last_updated")
    public String lastUpdated;
    @JsonProperty("quote")
    public Quote quote;
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
