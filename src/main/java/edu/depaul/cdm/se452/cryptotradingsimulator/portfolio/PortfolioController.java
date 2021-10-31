package edu.depaul.cdm.se452.cryptotradingsimulator.portfolio;

import edu.depaul.cdm.se452.cryptotradingsimulator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioRepository repo;

    @Autowired
    private AppCacheRepository appCacheRepository;

    @GetMapping
    public String showAll(Model model) {
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        Iterable<Portfolio> portfolios = repo.findByUserId(1L);
        model.addAttribute("portfolios", portfolios);
        model.addAttribute("tradingEngineService", tradingService);
        return "portfolios/list";
    }
}
