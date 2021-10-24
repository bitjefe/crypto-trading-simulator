package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Cryptocurrency {
    @Id
    private String ticker;
}
