package edu.depaul.cdm.se452.cryptotradingsimulator;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class AdminMetric {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private LocalDateTime createdAt;
}
