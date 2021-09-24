package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "start_date")
    private OffsetDateTime startDate;

    private Double balance;
}
