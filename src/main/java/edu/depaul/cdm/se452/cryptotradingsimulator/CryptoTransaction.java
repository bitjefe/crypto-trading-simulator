package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class CryptoTransaction {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "cryptocurrencyTicker")
    private Cryptocurrency cryptocurrency;

    private Double quantity;

    private Double pricePerCoin;

    private Boolean isPurchase;

    private LocalDateTime tradeDate;

    public void process(TradingEngineService tradingEngine) throws IllegalTransactionException {
        Boolean isSale = !this.isPurchase;
        if (isSale) {
            assertUserOwnsEnoughCryptocurrency();
        }
        this.pricePerCoin = cryptocurrency.getPrice(tradingEngine);
        Double transactionPrice = this.pricePerCoin * this.quantity;
        transactionPrice = this.isPurchase ? (transactionPrice * -1.0) : transactionPrice;
        this.portfolio.adjustBalance(transactionPrice);
    }

    private void assertUserOwnsEnoughCryptocurrency() throws IllegalTransactionException {
        if (!portfolio.isCryptoHoldingQuantityPositive(this.quantity, this.cryptocurrency.getTicker())) {
            throw new IllegalTransactionException("Cannot sell more Cryptocurrency than user owns!");
        }
    }

    public String getTransactionType() {
        return this.isPurchase ? "Buy" : "Sell";
    }

    public Double getTransactionValue() {
        return this.quantity * this.pricePerCoin;
    }

    public String getCryptocurrencyTicker() {
        return this.getCryptocurrency().getTicker();
    }
}
