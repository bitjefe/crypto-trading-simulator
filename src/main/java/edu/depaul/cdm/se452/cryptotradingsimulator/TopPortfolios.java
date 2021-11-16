package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "portfolios_ranking")
public class TopPortfolios implements Comparable<TopPortfolios> {

    private String name;

    private long id;

    @Id
    @Column
    private Double score;

    public void rankPortfolios(TopPortfoliosRepository topPortfoliosRep, PortfolioRepository portfoliorep,
            AppCacheRepository appCacheRepository) {
        // HashMap<Double, Long> portfolioHashMap = new HashMap<Double, Long>();
        List<TopPortfolios> topPortfolioList = new ArrayList<>();
        // Portfolio portfolio = new Portfolio();
        TradingEngineService tradingService = new RealTradingEngineService(appCacheRepository);

        portfoliorep.findAll().forEach((portfolio) -> {
            Double profitLoss = portfolio.getProfitLoss(tradingService);
            // portfolioHashMap.put(profitLoss, portfolio.getId());
            TopPortfolios temp = new TopPortfolios();
            temp.setScore(profitLoss);
            temp.setId(portfolio.getId());
            temp.setName(portfolio.getName());
            topPortfolioList.add(temp);
        });

        Collections.sort(topPortfolioList);
        Collections.reverse(topPortfolioList);
        System.out.println(topPortfolioList);
        for (TopPortfolios pf : topPortfolioList) {
            topPortfoliosRep.save(pf);
        }

    }

    @Override
    public int compareTo(TopPortfolios s) {
        return score.compareTo(s.getScore());
    }

}