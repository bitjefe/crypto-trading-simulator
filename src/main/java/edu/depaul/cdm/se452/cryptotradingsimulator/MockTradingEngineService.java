package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashMap;

public class MockTradingEngineService implements TradingEngineService {
    private HashMap<String, Double> mockPrices = new HashMap<>();

    @Override
    public Double fetchRemotePrice(String cryptoTicker) {
        return mockPrices.get(cryptoTicker);
    }

    @Override
    public HashMap<String, Double> getPrices() {
        return mockPrices;
    }

    public void setMockPrice(String cryptoTicker, Double mockPrice) {
        mockPrices.put(cryptoTicker, mockPrice);
    }
}
