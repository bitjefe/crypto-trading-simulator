package edu.depaul.cdm.se452.cryptotradingsimulator.portfolio;

import edu.depaul.cdm.se452.cryptotradingsimulator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioRepository repo;

    @Autowired
    private AppCacheRepository appCacheRepository;

    @Autowired
    private UserAuthenticationRepository userAuthRepo;

    @Autowired
    private CryptocurrencyRepository cryptoRepo;

    @Autowired
    private CryptoTransactionRepository cryptoTransactionRepository;

    private Long mockUserId = 1L;

    @GetMapping
    public String showAll(Model model) {
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        Iterable<Portfolio> portfolios = repo.findByUserId(mockUserId);
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/list";
    }

    @GetMapping(path="/add")
    public String add(Model model) {
        model.addAttribute("portfolio", new Portfolio());
        return "portfolios/add";
    }

    @GetMapping(path="/{id}")
    public String viewOne(@PathVariable("id") String portfolioId, Model model) {
        System.out.println("cryptoTransactionRepository = " + cryptoTransactionRepository.findAll());
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("portfolio", repo.findById(Long.parseLong(portfolioId)).get());
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/view";
    }

    @GetMapping(path="/{id}/tradingDashboard")
    public String tradingDashboard(@PathVariable("id") String portfolioId, Model model) {
        Portfolio portfolio = repo.findById(Long.parseLong(portfolioId)).get();
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("portfolio", portfolio);
        model.addAttribute("tradingEngineService", tradingService);
        model.addAttribute("coins", cryptoRepo.findAll());
        model.addAttribute("cryptoTransaction", cryptoTransaction);
        return "portfolios/trading-buy-sell";
    }

    @PostMapping
    public String savePortfolio(@ModelAttribute("portfolio") Portfolio portfolio, BindingResult bindingResult, @RequestBody MultiValueMap<String, String> formData) {
        String endDate = formData.get("endDate").get(0);
        portfolio.setStartDate(LocalDateTime.now());
        portfolio.setEndDate(LocalDate.parse(endDate).atStartOfDay());
        portfolio.setBalance(portfolio.getStartingBalance());
        portfolio.setUser(userAuthRepo.findById(mockUserId).get());
        repo.save(portfolio);
        return "redirect:/portfolio";
    }

    @PostMapping(path="{id}/transaction")
    public String saveTransaction(@ModelAttribute("cryptoTransaction") CryptoTransaction cryptoTransaction, @PathVariable("id") String portfolioId,
                                  BindingResult bindingResult, @RequestBody MultiValueMap<String, String> formData) {
        String ticker = formData.get("ticker").get(0);
        Portfolio p = repo.findById(Long.parseLong(portfolioId)).get();
        Boolean isPurchase = formData.get("buy-radio").size() > 0;
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        Cryptocurrency c = cryptoRepo.findById(ticker).get();
        cryptoTransaction.setCryptocurrency(c);
        cryptoTransaction.setPricePerCoin(c.getPrice(tradingService));
        cryptoTransaction.setIsPurchase(isPurchase);
        cryptoTransaction.setTradeDate(LocalDateTime.now());
        cryptoTransaction.setPortfolio(p);
        System.out.println("p.fancyToString(); = " + p.getCryptoTransactions());
        try {
            cryptoTransaction.process(tradingService);
        } catch (IllegalTransactionException e) {
            e.printStackTrace();
        }

        cryptoTransactionRepository.save(cryptoTransaction);
        repo.save(cryptoTransaction.getPortfolio());
        return "redirect:/portfolio/" + portfolioId;
    }
}
