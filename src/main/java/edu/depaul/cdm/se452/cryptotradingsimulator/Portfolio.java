package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double balance;
}
