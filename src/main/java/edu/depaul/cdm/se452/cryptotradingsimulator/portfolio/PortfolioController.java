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
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("portfolio", repo.findById(Long.parseLong(portfolioId)).get());
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/view";
    }

    @GetMapping(path="/{id}/tradingDashboard")
    public String tradingDashboard(@PathVariable("id") String portfolioId, Model model) {
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("portfolio", repo.findById(Long.parseLong(portfolioId)).get());
        model.addAttribute("tradingEngineService", tradingService);
        model.addAttribute("coins", cryptoRepo.findAll());
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
}
