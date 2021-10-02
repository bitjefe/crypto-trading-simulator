package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CryptoTransaction {
    @Id
    @GeneratedValue
    private long id;

    private String cryptocurrencyTicker;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private Double quantity;

    private Double pricePerCoin;

    private Boolean isPurchase;

    public void process(TradingEngineService tradingEngine) throws IllegalTransactionException {
        Boolean isSale = !this.isPurchase;
        if (isSale) {
            assertUserOwnsEnoughCryptocurrency();
        }
        this.pricePerCoin = tradingEngine.fetchRemotePrice(this.cryptocurrencyTicker);
        Double transactionPrice = this.pricePerCoin * this.quantity;
        transactionPrice = this.isPurchase ? (transactionPrice * -1.0) : transactionPrice;
        this.portfolio.adjustBalance(transactionPrice);
    }

    private void assertUserOwnsEnoughCryptocurrency() throws IllegalTransactionException {
        if (!portfolio.isCryptoHoldingQuantityPositive(this.quantity, this.cryptocurrencyTicker)) {
            throw new IllegalTransactionException("Cannot sell more Cryptocurrency than user owns!");
        }
    }
}
