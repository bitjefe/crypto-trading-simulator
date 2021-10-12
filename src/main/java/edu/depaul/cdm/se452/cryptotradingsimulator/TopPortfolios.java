package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "portfolios_ranking")
public class TopPortfolios implements Comparable<TopPortfolios> {
    @Id
    private long id;

    @Column
    private Double score;
    /*
     * public TopPortfolios(Double profitLoss, long id) { this.score = profitLoss;
     * this.id = id; }
     */

    public void rankPortfolios(TopPortfoliosRepository topPortfoliosRep, PortfolioRepository portfoliorep) {
        // HashMap<Double, Long> portfolioHashMap = new HashMap<Double, Long>();
        List<TopPortfolios> topPortfolioList = new ArrayList<>();

        portfoliorep.findAll().forEach((portfolio) -> {
            Double profitLoss = portfolio.getBalance() - 100000.0;
            // portfolioHashMap.put(profitLoss, portfolio.getId());
            TopPortfolios temp = new TopPortfolios();
            temp.setScore(profitLoss);
            temp.setId(portfolio.getId());
            topPortfolioList.add(temp);
        });
        // Collections.reverseOrder(portfolioHashMap);
        Collections.sort(topPortfolioList);
        Collections.reverse(topPortfolioList);
        System.out.println(topPortfolioList);
        for (TopPortfolios pf : topPortfolioList) {
            topPortfoliosRep.save(pf);
        }
        System.out.println(topPortfoliosRep);
    }

    @Override
    public int compareTo(TopPortfolios s) {
        // TODO Auto-generated method stub
        return score.compareTo(s.getScore());
    }

}