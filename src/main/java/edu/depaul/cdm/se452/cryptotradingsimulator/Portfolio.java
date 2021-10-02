package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double balance;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<CryptoTransaction> cryptoTransactions;

    public void adjustBalance(Double transactionPrice) {
        this.balance = this.balance + transactionPrice;
    }
}
