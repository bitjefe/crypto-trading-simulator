package edu.depaul.cdm.se452.cryptotradingsimulator;

public interface Cacheable {
    String getCacheKey();

    String getSerializedValue();

    String deserialize(String val);
}
