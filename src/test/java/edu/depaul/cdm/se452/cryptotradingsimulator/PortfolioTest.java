package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PortfolioTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PortfolioRepository repository;

    @Test
    void shouldCreatePortfolio() {
        Portfolio newRecord = new Portfolio();
        Double expectedBalance = 100.00;
        newRecord.setBalance(expectedBalance);
        LocalDateTime expectedStartDate = LocalDateTime.parse("2021-07-23T19:20:21");
        newRecord.setStartDate(expectedStartDate);
        entityManager.persist(newRecord);

        Portfolio dbRecord = repository.findById(newRecord.getId()).get();
        assertThat(dbRecord.getBalance()).isEqualTo(expectedBalance);
        assertThat(dbRecord.getStartDate()).isEqualTo(expectedStartDate);
    }
}
