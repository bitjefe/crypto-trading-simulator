package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Data
@Entity
public class Cryptocurrency {
    @Id
    private String ticker;

    public Double getPrice(TradingEngineService tradingService) {
        return tradingService.fetchRemotePrice(this.ticker);
    }
}
