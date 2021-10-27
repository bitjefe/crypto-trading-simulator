package edu.depaul.cdm.se452.cryptotradingsimulator;

import java.sql.Date;
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
    private String companyId;
    private Date startDate;
    private Date endDate;
    private Double pricePerAd;
    private String message;
    private String logo;
}