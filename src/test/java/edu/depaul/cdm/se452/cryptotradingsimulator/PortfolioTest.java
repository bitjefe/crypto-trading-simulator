package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PortfolioTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PortfolioRepository repository;

    private Portfolio createTestRecord(Double expectedBalance, LocalDateTime expectedStartDate) {
        Portfolio newRecord = new Portfolio();
        newRecord.setBalance(expectedBalance);
        newRecord.setStartDate(expectedStartDate);
        newRecord.setCryptoTransactions(Arrays.asList());
        entityManager.persistAndFlush(newRecord);
        return newRecord;
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

        CryptoTransaction transaction = new CryptoTransaction();
        transaction.setCryptocurrencyTicker("DOGE");
        transaction.setPortfolio(newRecord);

        entityManager.persistAndFlush(transaction);
        entityManager.refresh(newRecord);
        assertThat(newRecord.getCryptoTransactions().size()).isGreaterThan(0);
    }
}
