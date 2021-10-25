package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashMap;

public class MockTradingEngineService implements TradingEngineService {
    private HashMap<String, Double> mockPrices = new HashMap<>();

    @Override
    public Double fetchRemotePrice(String cryptoTicker) {
        return mockPrices.get(cryptoTicker);
    }

    public HashMap<String, Double> getPrices() {
        return mockPrices;
    }

    @Override
    public String getName(String ticker) {
        return "fake_name";
    }

    @Override
    public Double hourPriceChange(String ticker) {
        return 1.0;
    }

    @Override
    public Double dayPriceChange(String ticker) {
        return 2.0;
    }

    @Override
    public Double weekPriceChange(String ticker) {
        return 3.0;
    }

    @Override
    public Double percentageOfMarket(String ticker) {
        return 3.0;
    }

    public void setMockPrice(String cryptoTicker, Double mockPrice) {
        mockPrices.put(cryptoTicker, mockPrice);
    }
}
