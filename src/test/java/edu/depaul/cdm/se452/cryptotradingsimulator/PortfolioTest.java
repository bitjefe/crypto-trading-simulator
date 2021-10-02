package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;

@DataJpaTest
public class PortfolioTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PortfolioRepository repository;

    private Portfolio createTestRecord(Double expectedBalance, LocalDateTime expectedStartDate) {
        Portfolio newRecord = new Portfolio();
        newRecord.setBalance(expectedBalance);
        newRecord.setStartingBalance(expectedBalance);
        newRecord.setStartDate(expectedStartDate);
        newRecord.setCryptoTransactions(Arrays.asList());
        entityManager.persistAndFlush(newRecord);
        return newRecord;
    }

    private void createTransaction(Portfolio newRecord, MockTradingEngineService mockTradingEngine, String ticker, Double quantity, Boolean isPurchase) {
        CryptoTransaction transaction = new CryptoTransaction();
        transaction.setCryptocurrencyTicker(ticker);
        transaction.setQuantity(quantity);
        transaction.setIsPurchase(isPurchase);
        transaction.setPortfolio(newRecord);

        transaction.process(mockTradingEngine);

        entityManager.persistAndFlush(transaction);
        entityManager.refresh(newRecord);
    }

    @Test
    void canCreatePortfolio() {
        Double expectedBalance = 100.00;
        LocalDateTime expectedStartDate = LocalDateTime.parse("2021-07-23T19:20:21");
        Portfolio newRecord = createTestRecord(expectedBalance, expectedStartDate);

        Portfolio dbRecord = repository.findById(newRecord.getId()).get();
        assertThat(dbRecord.getBalance()).isEqualTo(expectedBalance);
        assertThat(dbRecord.getStartDate()).isEqualTo(expectedStartDate);
    }

    @Test
    void canPurchaseCryptocurrency() throws InterruptedException {
        Double expectedBalance = 100.00;
        LocalDateTime expectedStartDate = LocalDateTime.parse("2021-07-23T19:20:21");
        Portfolio newRecord = createTestRecord(expectedBalance, expectedStartDate);
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 3.0);
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, true);
        assertThat(newRecord.getCryptoTransactions().size()).isGreaterThan(0);
        assertThat(newRecord.getBalance()).isCloseTo(94.0, offset(0.1));
    }

    @Test
    void canComputeProfitLoss() {
        Double expectedBalance = 100.00;
        LocalDateTime expectedStartDate = LocalDateTime.parse("2021-07-23T19:20:21");
        Portfolio newRecord = createTestRecord(expectedBalance, expectedStartDate);
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 3.0);

        // User purchases 2 DOGE for $3 per coin
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, true);

        // User purchases 3 BTC for $5 per coin
        mockTradingEngine.setMockPrice("BTC", 3.0);
        createTransaction(newRecord, mockTradingEngine, "BTC", 5.0, true);

        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(0.0, offset(0.1));

        // Price of DOGE spikes to $10 per coin, profit = $14
        mockTradingEngine.setMockPrice("DOGE", 10.0);
        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(14.0, offset(0.1));

        // User sells DOGE at $10 per coin, profit remains $14
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, false);
        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(14.0, offset(0.1));
    }
}
