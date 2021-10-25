package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.HashMap;
import java.util.Map;

public interface TradingEngineService {
    public Double fetchRemotePrice(String cryptoTicker);

    public Map getPrices();

    String getName(String ticker);

    Double hourPriceChange(String ticker);

    Double dayPriceChange(String ticker);

    Double weekPriceChange(String ticker);

    Double percentageOfMarket(String ticker);
}
