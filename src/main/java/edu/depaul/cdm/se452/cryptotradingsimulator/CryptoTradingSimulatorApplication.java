package edu.depaul.cdm.se452.cryptotradingsimulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptoTradingSimulatorApplication {
    private static final Logger log = LoggerFactory.getLogger(CryptoTradingSimulatorApplication.class);

    @Value("${app.greeting}")
    private String greeting;

    @Bean
    public CommandLineRunner printEnvironmentVariable() {
        log.info("--- printEnvironmentVariable ---");
        return (args) -> {
            log.info("Hello world");
            log.info(greeting);
            log.info("---");
        };
    }

    @Bean
    public CommandLineRunner printLombokPortfolio() {
        log.info("--- printLombokPortfolio ---");
        return (args) -> {
            log.info(String.valueOf(new Portfolio()));
            log.info("---");
        };
    }

    @Bean
    public CommandLineRunner printPortfolios(PortfolioRepository repository) {
        log.info("--- printPortfolios ---");
        return (args) -> {
            log.info(String.valueOf(repository.findAll()));
            log.info(String.valueOf(repository.findById(1L).get().getCryptoTransactions()));
            log.info("---");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoTradingSimulatorApplication.class, args);
    }
}
