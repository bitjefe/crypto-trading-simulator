package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.DecimalFormat;

@Data
@Entity
public class Cryptocurrency {
    @Id
    private String ticker;

    public Double getPrice(TradingEngineService tradingService) {
        return tradingService.fetchRemotePrice(this.ticker);
    }

    public String getName(TradingEngineService tradingService) {
        return tradingService.getName(this.ticker);
    }

    public Double getPriceChange1h(TradingEngineService tradingService) {
        return tradingService.hourPriceChange(this.ticker);
    }

    public Double getPriceChange1d(TradingEngineService tradingService) {
        return tradingService.dayPriceChange(this.ticker);
    }

    public Double getPriceChange7d(TradingEngineService tradingService) {
        return tradingService.weekPriceChange(this.ticker);
    }

    public Double getMarketCapDominance(TradingEngineService tradingService) {
        return tradingService.percentageOfMarket(this.ticker);
    }

    public String getFormattedPrice(TradingEngineService tradingService) {
        Double price = this.getPrice(tradingService);
        DecimalFormat f = new DecimalFormat("##.00");
        return String.format("%s - %s - $%s", this.getName(tradingService), this.getTicker(), f.format(price));
    }
}
