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
import java.util.UUID;

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

    @Autowired
    private AdminMetricRepository adminMetricRepository;

    private Long mockUserId = 1L;

    @GetMapping
    public String showAll(Model model) {
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        Iterable<Portfolio> portfolios = repo.findByUserId(mockUserId);
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/list";
    }

    @GetMapping(path = "/add")
    public String add(Model model) {
        model.addAttribute("portfolio", new Portfolio());
        return "portfolios/add";
    }

    @GetMapping(path = "/{id}")
    public String viewOne(@PathVariable("id") String portfolioId, Model model) {
        System.out.println("cryptoTransactionRepository = " + cryptoTransactionRepository.findAll());
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("portfolio", repo.findById(Long.parseLong(portfolioId)).get());
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/view";
    }

    @GetMapping(path = "/{id}/tradingDashboard")
    public String tradingDashboard(@PathVariable("id") String portfolioId, Model model) {
        Portfolio portfolio = repo.findById(Long.parseLong(portfolioId)).get();
        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setId(UUID.randomUUID());
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

    @PostMapping(path = "transaction")
    public String saveTransaction(@ModelAttribute("cryptoTransaction") CryptoTransaction cryptoTransaction, @RequestParam(required = false) String portfolioId,
                                  BindingResult bindingResult, @RequestBody MultiValueMap<String, String> formData) throws IllegalTransactionException {
        String ticker = formData.get("ticker").get(0);
        Portfolio p = repo.findById(Long.parseLong(portfolioId)).get();
        Boolean isPurchase = formData.get("trade-radio").get(0).equals("purchase");
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        Cryptocurrency c = cryptoRepo.findById(ticker).get();
        cryptoTransaction.setCryptocurrency(c);
        cryptoTransaction.setPricePerCoin(c.getPrice(tradingService));
        cryptoTransaction.setIsPurchase(isPurchase);
        cryptoTransaction.setTradeDate(LocalDateTime.now());
        cryptoTransaction.setPortfolio(p);
        cryptoTransaction.process(tradingService);

        AdminMetric m = new AdminMetric();
        m.setName("trade_made");
        m.setCreatedAt(LocalDateTime.now());
        adminMetricRepository.save(m);
        cryptoTransactionRepository.save(cryptoTransaction);
        repo.save(cryptoTransaction.getPortfolio());
        return "redirect:/portfolio/" + portfolioId;
    }
}
