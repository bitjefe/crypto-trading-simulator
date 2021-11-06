package edu.depaul.cdm.se452.cryptotradingsimulator.coins;

import edu.depaul.cdm.se452.cryptotradingsimulator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coins")
public class CoinsController {
    @Autowired
    private PortfolioRepository repo;

    @Autowired
    private AppCacheRepository appCacheRepository;

    @Autowired
    private CryptocurrencyRepository cryptoRepo;

    private Long mockUserId = 1L;

    @GetMapping()
    public String showAll(Model model) {
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);
        model.addAttribute("coins", cryptoRepo.findAll());
        model.addAttribute("tradingEngineService", tradingService);
        return "coins/list";
    }
}
