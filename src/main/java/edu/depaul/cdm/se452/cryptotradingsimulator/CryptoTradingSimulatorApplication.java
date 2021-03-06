package edu.depaul.cdm.se452.cryptotradingsimulator;

//import org.graalvm.compiler.core.common.cfg.Loop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.depaul.cdm.se452.cryptotradingsimulator.userInfo.UserInfo;
import edu.depaul.cdm.se452.cryptotradingsimulator.userInfo.UserInfoRepository;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class CryptoTradingSimulatorApplication {
    private static final Logger log = LoggerFactory.getLogger(CryptoTradingSimulatorApplication.class);

    @Value("${app.greeting}")
    private String greeting;

    @Bean
    public CommandLineRunner addUserInfo(UserInfoRepository repository) {
        log.info("Creating initial noSql user info entry for user 2");
        return (args) -> {
            if (repository.findAll().size() == 0) {
                UserInfo userInfoObj = new UserInfo();
                userInfoObj.setUserId(2L);
                userInfoObj.setUserName("Jeff");
                userInfoObj.setDateOfBirth("12-25-2021");
                userInfoObj.setPhone("318-331-3161");
                repository.save(userInfoObj);
            }
            log.info("--- END insert initial user info data ---");
        };
    }

    // @Bean
    public CommandLineRunner printLombokPortfolio() {
        log.info("--- printLombokPortfolio ---");
        return (args) -> {
            // log.info(String.valueOf(new Portfolio()));
            log.info("---");
        };
    }

    // @Bean
    public CommandLineRunner printPortfolios(PortfolioRepository repository) {
        log.info("--- printPortfolios ---");
        return (args) -> {
            // log.info(String.valueOf(repository.findAll()));
            // log.info(String.valueOf(repository.findById(1L).get().getCryptoTransactions()));
            log.info("---");
        };
    }

    public CommandLineRunner printCacheItems(AppCacheRepository repository) {
        return (args) -> {
            log.info("--- printCacheItems ---");
            log.info("--- Making a new API call, is the result in cache and non-expired? ---");
            MockApiCall m = new MockApiCall("20190613");
            log.info("Cache hit: {}", AppCache.isCached(m.getCacheKey(), repository));

            Integer expirationTimeSeconds = 5;
            log.info("--- Making API call and caching item ... ---");
            AppCache.cacheItem(m.getCacheKey(), m.veryExpensiveSlowAPICall(), expirationTimeSeconds, repository);

            log.info("--- Making the same API call, is the result already in cache? ---");
            log.info("Cache hit: {}", AppCache.isCached(m.getCacheKey(), repository));
            log.info("Cache value: {}", AppCache.getCacheValue(m.getCacheKey(), repository));
            log.info("---");
        };
    }

    // @Bean
    public CommandLineRunner printTransactions(PortfolioRepository portfolioRepository,
            CryptoTransactionRepository transactionRepository, CryptocurrencyRepository cryptoRepo, EntityManager em) {
        log.info("--- printTransactions ---");
        MockTradingEngineService mockTradingEngine = new MockTradingEngineService();
        mockTradingEngine.setMockPrice("BTC", 1000.00);
        mockTradingEngine.setMockPrice("ETH", 500.00);
        return (args) -> {
            Portfolio newRecord = new Portfolio();
            newRecord.setStartingBalance(100000.00);
            newRecord.setBalance(100000.00);
            newRecord.setCryptoTransactions(Arrays.asList());
            portfolioRepository.save(newRecord);

            log.info("--- New portfolio created! ---");
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");

            log.info("--- User purchases 2 BTC and 3 ETH ---");
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "BTC", 2.00,
                    true, cryptoRepo);
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "ETH", 3.00,
                    true, cryptoRepo);
            newRecord = portfolioRepository.findById(newRecord.getId()).get();
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");

            log.info("--- BTC and ETH prices spike! ---");
            mockTradingEngine.setMockPrice("BTC", 2000.00);
            mockTradingEngine.setMockPrice("ETH", 1000.00);
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");

            log.info("--- User decides to sell their current holdings ---");
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "BTC", 2.00,
                    false, cryptoRepo);
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "ETH", 3.00,
                    false, cryptoRepo);
            newRecord = portfolioRepository.findById(newRecord.getId()).get();
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");
        };
    }

    // @Bean
    public CommandLineRunner fetchTop10Coins(AppCacheRepository repo, CryptocurrencyRepository cryptoRepo) {
        log.info("--- fetchTop10Coins ---");
        return (args) -> {
            RealTradingEngineService s = new RealTradingEngineService(repo);
            log.info("--- Recent Coin Prices ---");
            log.info(String.valueOf(cryptoRepo.findById("BTC")));
            log.info(String.valueOf(s.getPrices()));
            log.info("---");
        };
    }

    private CryptoTransaction createTransaction(Portfolio newRecord, MockTradingEngineService mockTradingEngine,
            CryptoTransactionRepository ctr, PortfolioRepository pr, String ticker, Double quantity, Boolean isPurchase,
            CryptocurrencyRepository cryptoRepo) {
        CryptoTransaction transaction = new CryptoTransaction();
        transaction.setCryptocurrency(cryptoRepo.findById(ticker).get());
        transaction.setQuantity(quantity);
        transaction.setIsPurchase(isPurchase);
        transaction.setPortfolio(newRecord);

        try {
            transaction.process(mockTradingEngine);
        } catch (IllegalTransactionException e) {
            e.printStackTrace();
        }

        ctr.save(transaction);
        pr.save(newRecord);

        return transaction;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoTradingSimulatorApplication.class, args);
    }
}
