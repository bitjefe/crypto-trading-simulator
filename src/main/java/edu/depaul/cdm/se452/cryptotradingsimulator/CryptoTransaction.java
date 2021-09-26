package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CryptoTransaction {
    @Id
    @GeneratedValue
    private long id;

    private String cryptocurrencyTicker;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
