package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;

@Data
public class Portfolio {
    private BigDecimal balance;

    private Time startDate;
}
