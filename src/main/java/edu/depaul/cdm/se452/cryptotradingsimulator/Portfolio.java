package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double balance;

    private Double startingBalance;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAuthentication user;

    public enum Status {
        COMPLETE, IN_PROGRESS
    }

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<CryptoTransaction> cryptoTransactions;

    public List<CryptoTransaction> getSortedCryptoTransactions() {
        return this.cryptoTransactions.stream().sorted((o1, o2) -> o2.getTradeDate().compareTo(o1.getTradeDate())).collect(Collectors.toList());
    }

    public void adjustBalance(Double transactionPrice) throws IllegalTransactionException {
        Double newBalance = this.balance + transactionPrice;
        if (newBalance < 0.0) {
            throw new IllegalTransactionException("Portfolio balance is too low!");
        }
        this.balance = newBalance;
    }

    public HashMap<String, Double> getCurrentHoldings() {
        HashMap<String, Double> currentHoldings = new HashMap<>();
        for (CryptoTransaction c : this.cryptoTransactions) {
            if (!currentHoldings.containsKey(c.getCryptocurrencyTicker())) {
                currentHoldings.put(c.getCryptocurrencyTicker(), 0.0);
            }
            Double quantityDelta = c.getQuantity();
            quantityDelta = c.getIsPurchase() ? quantityDelta : (quantityDelta * -1);
            Double prevQuantity = currentHoldings.get(c.getCryptocurrencyTicker());
            currentHoldings.put(c.getCryptocurrencyTicker(), prevQuantity + quantityDelta);
        }
        return currentHoldings;
    }

    public Double getProfitLoss(TradingEngineService tradingService) {
        return getCurrentPortfolioValue(tradingService) - this.startingBalance;
    }

    public Double getCurrentPortfolioValue(TradingEngineService tradingService) {
        return this.balance + getCurrentHoldingNetWorth(tradingService);
    }

    private Double getCurrentHoldingNetWorth(TradingEngineService tradingService) {
        HashMap<String, Double> currentHoldings = this.getCurrentHoldings();
        Double currentHoldingNetWorth = 0.0;
        for (Map.Entry<String, Double> entry : currentHoldings.entrySet()) {
            String cryptoTicker = entry.getKey();
            Double cryptoHoldings = entry.getValue();
            currentHoldingNetWorth += tradingService.fetchRemotePrice(cryptoTicker) * cryptoHoldings;
        }
        return currentHoldingNetWorth;
    }

    public boolean isCryptoHoldingQuantityPositive(Double quantity, String cryptocurrencyTicker) {
        if (!this.getCurrentHoldings().containsKey(cryptocurrencyTicker)) {
            return false;
        }
        return this.getCurrentHoldings().get(cryptocurrencyTicker) >= quantity;
    }

    public Status getStatus() {
        if (this.endDate.isBefore(LocalDateTime.now())) {
            return Status.COMPLETE;
        }

        return Status.IN_PROGRESS;
    }

    public String getFormattedStatus() {
        switch (this.getStatus()) {
            case COMPLETE:
                return "Complete";
            case IN_PROGRESS:
                return "In-Progress";
            default:
                return "Unknown";
        }
    }

    public void fancyToString(Logger log, TradingEngineService tradingService) {
        log.info("Current Balance: {}", getBalance());
        log.info("Current Holdings: {}", getCurrentHoldings());
        log.info("Current Prices: {}", tradingService.getPrices());
        log.info("Current Portfolio Value: {}", getCurrentPortfolioValue(tradingService));
        log.info("Current Profit/Loss: {}", getProfitLoss(tradingService));
        getCryptoTransactions().stream().forEach(s -> System.out.println("s = " + s));
    }
}
