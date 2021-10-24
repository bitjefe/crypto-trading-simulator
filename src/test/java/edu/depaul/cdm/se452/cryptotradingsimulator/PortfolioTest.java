package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PortfolioTest {
    @Autowired
    private PortfolioRepository repository;

    @Autowired
    private CryptocurrencyRepository cryptoRepo;

    @Autowired
    private CryptoTransactionRepository cryptoTransactionRepo;

    @MockBean(name = "printCacheItems")
    CommandLineRunner mongoMock;

    private Portfolio createTestRecord(Double expectedBalance, LocalDateTime expectedStartDate) {
        Portfolio newRecord = new Portfolio();
        newRecord.setBalance(expectedBalance);
        newRecord.setStartingBalance(expectedBalance);
        newRecord.setStartDate(expectedStartDate);
        newRecord.setCryptoTransactions(Arrays.asList());
        repository.save(newRecord);
        return newRecord;
    }

    private void createTransaction(Portfolio newRecord, MockTradingEngineService mockTradingEngine, String ticker, Double quantity, Boolean isPurchase) throws IllegalTransactionException {
        CryptoTransaction transaction = new CryptoTransaction();
        transaction.setCryptocurrency(cryptoRepo.findById(ticker).get());
        transaction.setQuantity(quantity);
        transaction.setIsPurchase(isPurchase);
        transaction.setPortfolio(newRecord);

        transaction.process(mockTradingEngine);
        cryptoTransactionRepo.save(transaction);
        repository.save(transaction.getPortfolio());
    }

    @Test
    void canCreatePortfolio() {
        Double expectedBalance = 100.00;
        LocalDateTime expectedStartDate = LocalDateTime.parse("2021-07-23T19:20:21");
        Portfolio newRecord = createTestRecord(expectedBalance, expectedStartDate);

        Portfolio dbRecord = repository.findById(newRecord.getId()).get();
        assertThat(dbRecord.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void canPurchaseCryptocurrency() throws InterruptedException, IllegalTransactionException {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 3.0);
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, true);
        newRecord = repository.findById(newRecord.getId()).get();
        assertThat(newRecord.getCryptoTransactions().size()).isGreaterThan(0);
        assertThat(newRecord.getBalance()).isCloseTo(94.0, offset(0.1));
    }

    @Test
    void canComputeProfitLoss() throws IllegalTransactionException {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 3.0);

        // User purchases 2 DOGE for $3 per coin
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, true);
        newRecord = repository.findById(newRecord.getId()).get();

        // User purchases 3 BTC for $5 per coin
        mockTradingEngine.setMockPrice("BTC", 3.0);
        createTransaction(newRecord, mockTradingEngine, "BTC", 5.0, true);
        newRecord = repository.findById(newRecord.getId()).get();

        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(0.0, offset(0.1));

        // Price of DOGE spikes to $10 per coin, profit = $14
        mockTradingEngine.setMockPrice("DOGE", 10.0);
        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(14.0, offset(0.1));

        // User sells DOGE at $10 per coin, profit remains $14
        createTransaction(newRecord, mockTradingEngine, "DOGE", 2.0, false);
        newRecord = repository.findById(newRecord.getId()).get();

        assertThat(newRecord.getProfitLoss(mockTradingEngine)).isCloseTo(14.0, offset(0.1));
    }

    @Test
    void throwsErrorIfUserBalanceIsTooLow() {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 101.0);
        assertThrows(IllegalTransactionException.class, () -> createTransaction(newRecord, mockTradingEngine, "DOGE", 1.0, true));
    }

    @Test
    void throwsErrorIfUserSellsCryptoTheyDontOwn() {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("DOGE", 101.0);
        assertThrows(IllegalTransactionException.class, () -> createTransaction(newRecord, mockTradingEngine, "DOGE", 1.0, false));
    }

    @Test
    void hasEndStatusCompleteIfPastEndDate() {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        newRecord.setEndDate(LocalDateTime.parse("2021-07-23T19:20:21"));
        assertThat(newRecord.getStatus()).isEqualTo(Portfolio.Status.COMPLETE);
    }

    @Test
    void hasEndStatusInCompleteIfBeforeEndDate() {
        Portfolio newRecord = createTestRecord(100.00, LocalDateTime.parse("2021-07-23T19:20:21"));
        newRecord.setEndDate(LocalDateTime.parse("2221-07-23T19:20:21"));
        assertThat(newRecord.getStatus()).isEqualTo(Portfolio.Status.IN_PROGRESS);
    }
}
