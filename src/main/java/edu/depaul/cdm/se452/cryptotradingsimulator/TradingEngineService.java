package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashMap;

public interface TradingEngineService {
    public Double fetchRemotePrice(String cryptoTicker);

    HashMap<String, Double> getPrices();
}
