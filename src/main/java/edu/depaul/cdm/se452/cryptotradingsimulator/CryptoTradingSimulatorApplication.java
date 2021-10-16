package edu.depaul.cdm.se452.cryptotradingsimulator;

//import org.graalvm.compiler.core.common.cfg.Loop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import java.util.Arrays;

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

            // log.info(String.valueOf(new Portfolio()));
            log.info("---");
        };
    }

   @Bean
    public CommandLineRunner printPortfolios(PortfolioRepository repository) {
        log.info("--- printPortfolios ---");
        return (args) -> {
            // log.info(String.valueOf(repository.findAll()));
            // log.info(String.valueOf(repository.findById(1L).get().getCryptoTransactions()));
            log.info("---");
        };
    }

    @Bean
    public CommandLineRunner userLogin(UserAuthenticationRepository repository) {
        log.info("--- userLogin ---");
        return (args) -> {
            UserAuthentication userAuth = new UserAuthentication();

            if (userAuth.signIn(repository) == true) {
                System.out.println("You just logged in, welcome!");
            } else {
                System.out.println("No account found. Please sign up below: ");
                userAuth.signUp(repository);
            }

            // log.info(String.valueOf(repository.findAll()));

            // log.info("---");
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

    @Bean
    public CommandLineRunner printTopPortfolios(TopPortfoliosRepository topPortfoliosRep,
            PortfolioRepository portfoliorep) {
        log.info("--- portofoliosRanking ---");
        return (args) -> {
            // System.out.println(portfoliorep.findById(1L));
            TopPortfolios topPortf = new TopPortfolios();
            topPortf.rankPortfolios(topPortfoliosRep, portfoliorep);

            log.info(String.valueOf(topPortfoliosRep.findAll()));

        };
    }

    @Bean
    public CommandLineRunner printTransactions(PortfolioRepository portfolioRepository, CryptoTransactionRepository transactionRepository, EntityManager em) {
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
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "BTC", 2.00, true);
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "ETH", 3.00, true);
            newRecord = portfolioRepository.findById(newRecord.getId()).get();
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");

            log.info("--- BTC and ETH prices spike! ---");
            mockTradingEngine.setMockPrice("BTC", 2000.00);
            mockTradingEngine.setMockPrice("ETH", 1000.00);
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");

            log.info("--- User decides to sell their current holdings ---");
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "BTC", 2.00, false);
            createTransaction(newRecord, mockTradingEngine, transactionRepository, portfolioRepository, "ETH", 3.00, false);
            newRecord = portfolioRepository.findById(newRecord.getId()).get();
            newRecord.fancyToString(log, mockTradingEngine);
            log.info("---");
        };
    }

    @Bean
    public CommandLineRunner fetchTop10Coins() {
        log.info("--- fetchTop10Coins ---");
        return (args) -> {
            log.info("Hello world");
            RealTradingEngineService s = new RealTradingEngineService();
            log.info("--- Recent Coin Prices ---");
            log.info(String.valueOf(s.fetchTopTenCoins()));
            s.fetchTopTenCoins();
            log.info("---");
        };
    }

    private CryptoTransaction createTransaction(Portfolio newRecord, MockTradingEngineService mockTradingEngine, CryptoTransactionRepository ctr, PortfolioRepository pr,
                                                String ticker, Double quantity, Boolean isPurchase) {
        CryptoTransaction transaction = new CryptoTransaction();
        transaction.setCryptocurrencyTicker(ticker);
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
