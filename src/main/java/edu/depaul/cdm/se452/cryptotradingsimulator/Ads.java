package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ads")
public class Ads {
    @Id
    private String id;  

    private String companyId;
    private String startDate;
    private String endDate;
    private Double pricePerAd;
    private String message;
    private String logo;
    private LocalDateTime createdAt;

    private LocalDate createdAtDay() {
        return this.createdAt.toLocalDate();
    }
}