
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
    "timestamp",
    "error_code",
    "error_message",
    "elapsed",
    "credit_count",
    "notice",
    "total_count"
})
@Generated("jsonschema2pojo")
@Data
public class Status {

    @JsonProperty("timestamp")
    public String timestamp;
    @JsonProperty("error_code")
    public Integer errorCode;
    @JsonProperty("error_message")
    public Object errorMessage;
    @JsonProperty("elapsed")
    public Integer elapsed;
    @JsonProperty("credit_count")
    public Integer creditCount;
    @JsonProperty("notice")
    public Object notice;
    @JsonProperty("total_count")
    public Integer totalCount;
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
